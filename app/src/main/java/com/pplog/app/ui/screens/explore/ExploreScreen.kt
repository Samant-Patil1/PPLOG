package com.pplog.app.ui.screens.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.navigation.NavController
import com.pplog.app.domain.model.Difficulty
import com.pplog.app.domain.model.Equipment
import com.pplog.app.domain.model.MuscleGroup
import com.pplog.app.ui.components.ExerciseDetailBottomSheet
import com.pplog.app.ui.components.ExerciseListItem
import com.pplog.app.ui.components.FilterChipGroup
import com.pplog.app.ui.components.FilterSection
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    navController: NavController,
    viewModel: ExploreViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var filtersExpanded by remember { mutableStateOf(true) }
    var selectedExerciseId by remember { mutableStateOf<String?>(null) }

    val filteredExercises = state.exercises.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explore Exercises") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search exercises") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )

            FilterSection(
                title = "Filters",
                expanded = filtersExpanded,
                onToggle = { filtersExpanded = !filtersExpanded }
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredExercises, key = { it.id }) { exercise ->
                    ExerciseListItem(
                        exercise = exercise,
                        onClick = { selectedExerciseId = exercise.id }
                    )
                }
            }
        }
    }

    selectedExerciseId?.let { id ->
        ExerciseDetailBottomSheet(
            exerciseId = id,
            onDismiss = { selectedExerciseId = null }
        )
    }
}
