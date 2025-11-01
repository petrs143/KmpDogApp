package cz.cvut.kmp.feature.breed.data

import cz.cvut.kmp.feature.breed.domain.Breed
import cz.cvut.kmp.feature.breed.domain.BreedRepository

class BreedRepositoryImpl(
    private val breedRemoteDataSource: BreedRemoteDataSource
) : BreedRepository {

    override suspend fun getBreeds(): List<Breed> {
        return breedRemoteDataSource.getBreeds()
    }
}
