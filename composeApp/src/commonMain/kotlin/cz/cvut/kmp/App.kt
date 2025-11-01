package cz.cvut.kmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cz.cvut.kmp.core.navigation.DogNavigationHost
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinConfiguration

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {
    KoinMultiplatformApplication(
        KoinConfiguration { modules(appModule) }
    ) {
        MaterialTheme {
            DogNavigationHost()
        }
    }
}
