package cz.cvut.kmp.feature.fact.domain

import kotlinx.coroutines.flow.Flow

interface FactRepository {

    suspend fun fetchFact(): Fact

    fun getLikedFacts(): Flow<List<Fact>>

    suspend fun likeFact(fact: Fact)
}
