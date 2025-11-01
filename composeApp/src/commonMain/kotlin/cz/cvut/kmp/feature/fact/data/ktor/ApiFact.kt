package cz.cvut.kmp.feature.fact.data.ktor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiFact(
    @SerialName("attributes")
    val attributes: Attributes
) {

    @Serializable
    data class Attributes(
        @SerialName("body")
        val body: String,
    )
}
