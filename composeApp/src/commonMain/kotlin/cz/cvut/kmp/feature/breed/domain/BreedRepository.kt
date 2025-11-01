package cz.cvut.kmp.feature.breed.domain

interface BreedRepository {

    suspend fun getBreeds(): List<Breed>
}
