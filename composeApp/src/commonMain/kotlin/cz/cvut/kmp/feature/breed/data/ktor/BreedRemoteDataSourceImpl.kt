package cz.cvut.kmp.feature.breed.data.ktor

import cz.cvut.kmp.core.network.DogApiClient
import cz.cvut.kmp.feature.breed.data.BreedRemoteDataSource
import cz.cvut.kmp.feature.breed.domain.Breed

class BreedRemoteDataSourceImpl(
    private val apiClient: DogApiClient
) : BreedRemoteDataSource {

    private val apiBreedMapper = ApiBreedMapper()

    override suspend fun getBreeds(): List<Breed> {
        return apiClient.get<List<ApiBreed>>("breeds")
            .map(apiBreedMapper::map)
    }
}
