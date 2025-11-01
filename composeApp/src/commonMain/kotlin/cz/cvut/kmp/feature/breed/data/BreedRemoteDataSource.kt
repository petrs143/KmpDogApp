package cz.cvut.kmp.feature.breed.data

import cz.cvut.kmp.feature.breed.domain.Breed

interface BreedRemoteDataSource {

    suspend fun getBreeds(): List<Breed>
}
