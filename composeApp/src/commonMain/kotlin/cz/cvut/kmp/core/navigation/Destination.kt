package cz.cvut.kmp.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination {

    @Serializable
    data object BreedList : Destination

    @Serializable
    data object Facts : Destination
}
