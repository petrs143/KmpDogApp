package cz.cvut.kmp.feature.fact.data

import cz.cvut.kmp.feature.fact.domain.Fact
import kotlinx.coroutines.flow.Flow

interface FactLocalDataSource {

    fun getFacts(): Flow<List<Fact>>

    suspend fun saveFact(fact: Fact)
}
