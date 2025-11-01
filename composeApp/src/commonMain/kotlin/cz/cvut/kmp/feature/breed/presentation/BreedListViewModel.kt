package cz.cvut.kmp.feature.breed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.kmp.feature.breed.domain.Breed
import cz.cvut.kmp.feature.breed.domain.BreedRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class BreedListViewModel(
    private val repository: BreedRepository,
) : ViewModel() {

    private val _screenState: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.Loading)

    val screenState: StateFlow<ScreenState> = _screenState
        .onStart { fetchData() }
        .stateIn(viewModelScope, SharingStarted.Lazily, ScreenState.Loading)

    private suspend fun fetchData() {
        _screenState.value = try {
            val breeds = repository.getBreeds()
            ScreenState.Loaded(breeds)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            ScreenState.Error(e)
        }
    }

    sealed interface ScreenState {
        object Loading : ScreenState
        data class Loaded(val breeds: List<Breed>) : ScreenState
        data class Error(val error: Throwable) : ScreenState
    }
}
