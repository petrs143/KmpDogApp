package cz.cvut.kmp.feature.breed.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cz.cvut.kmp.core.navigation.Destination
import cz.cvut.kmp.feature.breed.domain.Breed
import kmpdogapp.composeapp.generated.resources.Res
import kmpdogapp.composeapp.generated.resources.error_prefix
import kmpdogapp.composeapp.generated.resources.female_weight
import kmpdogapp.composeapp.generated.resources.hypoallergenic
import kmpdogapp.composeapp.generated.resources.kg
import kmpdogapp.composeapp.generated.resources.life_span
import kmpdogapp.composeapp.generated.resources.male_weight
import kmpdogapp.composeapp.generated.resources.years
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BreedListScreen(
    navController: NavController,
    viewModel: BreedListViewModel = koinViewModel()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    BreedListScreen(
        screenState = screenState,
        onFactsClick = { navController.navigate(Destination.Facts) }
    )
}

@Composable
private fun BreedListScreen(
    screenState: BreedListViewModel.ScreenState,
    onFactsClick: () -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            Button(onClick = onFactsClick) {
                Text("Facts")
            }
        }
    ) { paddingValues ->
        when (screenState) {
            is BreedListViewModel.ScreenState.Loaded -> Loaded(screenState, paddingValues)
            is BreedListViewModel.ScreenState.Error -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(Res.string.error_prefix, screenState.error.message ?: ""))
            }

            BreedListViewModel.ScreenState.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun Loaded(
    screenState: BreedListViewModel.ScreenState.Loaded,
    padding: PaddingValues
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = padding.calculateTopPadding() + 16.dp,
            bottom = padding.calculateBottomPadding() + 16.dp,
            start = padding.calculateStartPadding(LocalLayoutDirection.current),
            end = padding.calculateEndPadding(LocalLayoutDirection.current),
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items(screenState.breeds) { breed ->
            BreedCard(breed)
        }
    }
}

@Composable
private fun BreedCard(breed: Breed) {
    val yearsUnit = stringResource(Res.string.years)
    val kgUnit = stringResource(Res.string.kg)

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = breed.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                if (breed.hypoallergenic) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = stringResource(Res.string.hypoallergenic),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = breed.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                InfoItem(
                    label = stringResource(Res.string.life_span),
                    value = "${breed.life.min}-${breed.life.max} $yearsUnit"
                )

                InfoItem(
                    label = stringResource(Res.string.male_weight),
                    value = "${breed.maleWeight.min}-${breed.maleWeight.max} $kgUnit"
                )

                InfoItem(
                    label = stringResource(Res.string.female_weight),
                    value = "${breed.femaleWeight.min}-${breed.femaleWeight.max} $kgUnit"
                )
            }
        }
    }
}

@Composable
private fun InfoItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}
