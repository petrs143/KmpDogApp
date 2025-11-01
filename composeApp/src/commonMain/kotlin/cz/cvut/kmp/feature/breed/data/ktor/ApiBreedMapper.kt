package cz.cvut.kmp.feature.breed.data.ktor

import cz.cvut.kmp.feature.breed.domain.Breed

class ApiBreedMapper {

    fun map(apiBreed: ApiBreed): Breed {
        return with(apiBreed.attributes) {
            Breed(
                name = name,
                description = description,
                hypoallergenic = hypoallergenic,
                life = life.let(::map),
                maleWeight = maleWeight.let(::map),
                femaleWeight = femaleWeight.let(::map),
            )
        }
    }

    private fun map(apiRange: ApiBreed.Attributes.Range): Breed.Range {
        return with(apiRange) {
            Breed.Range(
                min = min,
                max = max,
            )
        }
    }
}
