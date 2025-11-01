package cz.cvut.kmp.feature.fact.data.ktor

import cz.cvut.kmp.core.network.DogApiClient
import cz.cvut.kmp.feature.fact.data.FactRemoteDataSource
import cz.cvut.kmp.feature.fact.domain.Fact

class FactRemoteDataSourceImpl(
    private val dogApiClient: DogApiClient
) : FactRemoteDataSource {

    private val apiFactMapper = ApiFactMapper()

    override suspend fun getFact(): Fact {
        return dogApiClient.get<List<ApiFact>>("facts")
            .first()
            .let(apiFactMapper::map)
    }
}
