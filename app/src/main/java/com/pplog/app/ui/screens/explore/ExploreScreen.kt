package com.pplog.app.ui.screens.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pplog.app.domain.model.Difficulty
import com.pplog.app.domain.model.Equipment
import com.pplog.app.domain.model.MuscleGroup
import com.pplog.app.ui.components.ExerciseCard
import com.pplog.app.ui.components.FilterChipGroup
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    navController: NavController,
    viewModel: ExploreViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explore Exercises") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Muscle group", style = MaterialTheme.typography.labelLarge)
            FilterChipGroup(
                items = MuscleGroup.entries,
                selected = state.muscleFilter,
                onSelected = viewModel::setMuscleFilter,
                label = { it.name.replace("_", " ") }
            )

            Text("Equipment", style = MaterialTheme.typography.labelLarge)
            FilterChipGroup(
                items = Equipment.entries,
                selected = state.equipmentFilter,
                onSelected = viewModel::setEquipmentFilter,
                label = { it.name.replace("_", " ") }
            )

            Text("Difficulty", style = MaterialTheme.typography.labelLarge)
            FilterChipGroup(
                items = Difficulty.entries,
                selected = state.difficultyFilter,
                onSelected = viewModel::setDifficultyFilter,
                label = { it.name.lowercase().replaceFirstChar { c -> c.uppercase() } }
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.exercises, key = { it.id }) { exercise ->
                    ExerciseCard(
                        exercise = exercise,
                        onClick = { navController.navigate("exercise_detail/${exercise.id}") }
                    )
                }
            }
        }
    }
}
