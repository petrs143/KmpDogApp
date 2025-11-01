package cz.cvut.kmp.feature.fact.data

import cz.cvut.kmp.feature.fact.domain.Fact
import cz.cvut.kmp.feature.fact.domain.FactRepository
import kotlinx.coroutines.flow.Flow

class FactRepositoryImpl(
    private val factRemoteDataSource: FactRemoteDataSource,
    private val factLocalDataSource: FactLocalDataSource
) : FactRepository {

    override suspend fun fetchFact(): Fact {
        return factRemoteDataSource.getFact()
    }

    override fun getLikedFacts(): Flow<List<Fact>> {
        return factLocalDataSource.getFacts()
    }

    override suspend fun likeFact(fact: Fact) {
        factLocalDataSource.saveFact(fact)
    }
}
