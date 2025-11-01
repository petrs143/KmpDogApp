package cz.cvut.kmp.feature.breed.data.ktor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiBreed(
    @SerialName("attributes")
    val attributes: Attributes
) {

    @Serializable
    data class Attributes(
        @SerialName("name")
        val name: String,
        @SerialName("description")
        val description: String,
        @SerialName("hypoallergenic")
        val hypoallergenic: Boolean,
        @SerialName("life")
        val life: Range,
        @SerialName("male_weight")
        val maleWeight: Range,
        @SerialName("female_weight")
        val femaleWeight: Range,
    ) {

        @Serializable
        data class Range(
            @SerialName("min")
            val min: Int,
            @SerialName("max")
            val max: Int,
        )
    }
}
