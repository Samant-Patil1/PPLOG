package com.pplog.app.ui.screens.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pplog.app.ui.components.ExerciseDetailBottomSheet
import com.pplog.app.ui.components.WorkoutSetCard
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(
    viewModel: WorkoutViewModel = koinViewModel()
) {
    val plan by viewModel.activePlan.collectAsState()
    val completedSets by viewModel.completedSets.collectAsState()
    var selectedExerciseId by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workout") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            plan?.days?.firstOrNull()?.let { day ->
                Text(
                    text = "Day ${day.dayNumber}: ${day.focus}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                day.exercises.forEach { exercise ->
                    WorkoutSetCard(
                        exercise = exercise,
                        completedSets = completedSets[exercise.exerciseId] ?: emptySet(),
                        onToggleSet = { viewModel.toggleSet(exercise.exerciseId, it) },
                        onExerciseClick = { selectedExerciseId = exercise.exerciseId }
                    )
                }
            } ?: Text(
                text = "No workout available. Build a plan first.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    selectedExerciseId?.let { id ->
        ExerciseDetailBottomSheet(
            exerciseId = id,
            onDismiss = { selectedExerciseId = null }
        )
    }
}
