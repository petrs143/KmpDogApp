package cz.cvut.kmp.feature.fact.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cz.cvut.kmp.feature.fact.domain.Fact
import kmpdogapp.composeapp.generated.resources.Res
import kmpdogapp.composeapp.generated.resources.ic_back
import kmpdogapp.composeapp.generated.resources.liked_facts
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FactScreen(
    navController: NavController,
    viewModel: FactViewModel = koinViewModel(),
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    FactScreen(
        screenState = screenState,
        onNavigateBack = { navController.navigateUp() },
        onLike = { viewModel.onLike(it) },
        onDislike = { viewModel.onDislike() }
    )
}

@Composable
private fun FactScreen(
    screenState: FactViewModel.ScreenState,
    onNavigateBack: () -> Unit,
    onLike: (Fact) -> Unit,
    onDislike: () -> Unit,
) {
    Scaffold(
        topBar = {
            IconButton(onClick = onNavigateBack, modifier = Modifier.statusBarsPadding()) {
                Icon(
                    painter = painterResource(Res.drawable.ic_back),
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            CurrentFact(
                currentFactState = screenState.currentFactState,
                modifier = Modifier.weight(.6f),
                onLike = onLike,
                onDislike = onDislike
            )

            Text(
                stringResource(Res.string.liked_facts),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            LazyColumn(
                modifier = Modifier.weight(.4f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(screenState.likedFacts) {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Text(it.text, modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun CurrentFact(
    currentFactState: FactViewModel.ScreenState.CurrentFactState,
    onLike: (Fact) -> Unit,
    onDislike: () -> Unit,
    modifier: Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        when (currentFactState) {
            is FactViewModel.ScreenState.CurrentFactState.Loaded -> LoadedCurrentFact(
                fact = currentFactState.fact,
                onLike = { onLike(currentFactState.fact) },
                onDislike = onDislike,
            )

            is FactViewModel.ScreenState.CurrentFactState.Error -> Text(currentFactState.error.message ?: "")
            FactViewModel.ScreenState.CurrentFactState.Loading -> CircularProgressIndicator()
        }
    }
}

@Composable
private fun LoadedCurrentFact(
    fact: Fact,
    onLike: () -> Unit,
    onDislike: () -> Unit
) {
    var offset by remember { mutableStateOf(Offset.Zero) }
    val dragThreshold = 250f

    val color by animateColorAsState(
        when {
            offset.x > dragThreshold -> Color.Green
            offset.x < -dragThreshold -> Color.Red
            else -> Color.Transparent
        }.copy(alpha = .4f)
    )

    Box(
        modifier = Modifier.pointerInput(Unit) {
            detectDragGestures(
                onDragEnd = {
                    if (offset.x > dragThreshold) {
                        onLike()
                    } else if (offset.x < -dragThreshold) {
                        onDislike()
                    }
                    offset = Offset.Zero
                },
                onDrag = { change, dragAmount ->
                    change.consume()
                    offset += dragAmount
                }
            )
        }.offset {
            offset.round()
        }.graphicsLayer {
            rotationZ = (offset.x / 60f).coerceIn(-20f, 20f)
        }.padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .background(color)
            .padding(32.dp)
    ) {
        Text(
            text = fact.text,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}
