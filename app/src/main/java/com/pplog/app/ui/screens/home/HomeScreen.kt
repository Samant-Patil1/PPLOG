package com.pplog.app.ui.screens.home

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pplog.app.ui.components.HeroCard
import com.pplog.app.ui.components.PrimaryButton
import com.pplog.app.ui.components.Stat
import com.pplog.app.ui.components.StatRow
import com.pplog.app.ui.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PPLOG", style = MaterialTheme.typography.headlineMedium) }
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
            HeroCard(
                title = "Welcome back",
                subtitle = "Ready to hit your goals today?"
            )

            StatRow(
                stats = listOf(
                    Stat(value = state.workoutsThisWeek.toString(), label = "This week"),
                    Stat(value = "${state.currentStreakDays}d", label = "Streak")
                )
            )

            state.activePlan?.let { plan ->
                HeroCard(
                    title = plan.name,
                    subtitle = "${plan.daysPerWeek} days per week"
                ) {
                    PrimaryButton(
                        text = "Start Today's Workout",
                        onClick = { navController.navigate(Screen.Workout.route) },
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            } ?: HeroCard(
                title = "No active plan",
                subtitle = "Build a custom plan that fits your schedule."
            ) {
                PrimaryButton(
                    text = "Build Plan",
                    onClick = { navController.navigate(Screen.PlanBuilder.route) },
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
