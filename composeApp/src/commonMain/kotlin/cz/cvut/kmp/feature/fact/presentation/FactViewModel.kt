package cz.cvut.kmp.feature.fact.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.kmp.feature.fact.domain.Fact
import cz.cvut.kmp.feature.fact.domain.FactRepository
import io.ktor.utils.io.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FactViewModel(
    private val factRepository: FactRepository
) : ViewModel() {

    private val currentFactStateStream = MutableStateFlow<ScreenState.CurrentFactState>(ScreenState.CurrentFactState.Loading)
    private val likedFactsStream = factRepository.getLikedFacts()
        .map { it.reversed() }

    val screenState = combine(
        currentFactStateStream,
        likedFactsStream,
        ::ScreenState
    ).onStart { fetchFact() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            ScreenState(ScreenState.CurrentFactState.Loading, emptyList())
        )

    private suspend fun fetchFact() {
        currentFactStateStream.value = ScreenState.CurrentFactState.Loading
        currentFactStateStream.value = try {
            val fact = factRepository.fetchFact()
            ScreenState.CurrentFactState.Loaded(fact)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            ScreenState.CurrentFactState.Error(e)
        }
    }

    fun onLike(fact: Fact) {
        viewModelScope.launch {
            factRepository.likeFact(fact)
            fetchFact()
        }
    }

    fun onDislike() {
        viewModelScope.launch {
            fetchFact()
        }
    }

    data class ScreenState(
        val currentFactState: CurrentFactState,
        val likedFacts: List<Fact>,
    ) {

        sealed interface CurrentFactState {

            data object Loading : CurrentFactState
            data class Loaded(val fact: Fact) : CurrentFactState
            data class Error(val error: Throwable) : CurrentFactState
        }
    }
}
