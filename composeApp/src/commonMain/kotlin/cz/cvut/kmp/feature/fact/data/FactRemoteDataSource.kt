package cz.cvut.kmp.feature.fact.data

import cz.cvut.kmp.feature.fact.domain.Fact

interface FactRemoteDataSource {

    suspend fun getFact(): Fact
}
