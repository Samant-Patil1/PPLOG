package com.pplog.app.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pplog.app.ui.components.PrimaryButton
import com.pplog.app.ui.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Welcome back",
                style = MaterialTheme.typography.headlineLarge
            )
            state.activePlan?.let { plan ->
                Text(
                    text = "Active plan: ${plan.name}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = "${plan.daysPerWeek} days/week",
                    style = MaterialTheme.typography.bodyLarge
                )
                PrimaryButton(
                    text = "Start Today's Workout",
                    onClick = { navController.navigate(Screen.Workout.route) },
                    modifier = Modifier.padding(top = 24.dp)
                )
            } ?: Text(
                text = "No active plan. Create one from the Plan tab.",
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
