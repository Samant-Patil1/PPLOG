package com.pplog.app.ui.screens.plan

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
import com.pplog.app.ui.components.PrimaryButton
import com.pplog.app.ui.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanScreen(
    navController: NavController,
    viewModel: PlanViewModel = koinViewModel()
) {
    val plan by viewModel.activePlan.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Plan") },
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
            plan?.let { p ->
                Text(
                    text = p.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "${p.daysPerWeek} days per week",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(p.days, key = { it.dayNumber }) { day ->
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Text(
                                text = "Day ${day.dayNumber}: ${day.focus}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            day.exercises.forEach { ex ->
                                Text(
                                    text = "${ex.exerciseName} - ${ex.sets} x ${ex.reps}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                                )
                            }
                        }
                    }
                }
            } ?: Text("No active plan yet.")

            PrimaryButton(
                text = "Build New Plan",
                onClick = { navController.navigate(Screen.PlanBuilder.route) },
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
