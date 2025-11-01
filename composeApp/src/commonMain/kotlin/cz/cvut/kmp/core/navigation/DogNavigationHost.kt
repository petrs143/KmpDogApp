package cz.cvut.kmp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.cvut.kmp.feature.breed.presentation.BreedListScreen
import cz.cvut.kmp.feature.fact.presentation.FactScreen

@Composable
fun DogNavigationHost() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Destination.BreedList) {
        composable<Destination.BreedList> {
            BreedListScreen(navController)
        }

        composable<Destination.Facts> {
            FactScreen(navController)
        }
    }
}
