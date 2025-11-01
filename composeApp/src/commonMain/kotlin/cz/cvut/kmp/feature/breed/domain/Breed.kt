package cz.cvut.kmp.feature.breed.domain

class Breed(
    val name: String,
    val description: String,
    val hypoallergenic: Boolean,
    val life: Range,
    val maleWeight: Range,
    val femaleWeight: Range,
) {

    data class Range(
        val min: Int,
        val max: Int,
    )
}
