package com.pplog.app.ui.screens.plan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pplog.app.domain.model.Equipment
import com.pplog.app.domain.model.Experience
import com.pplog.app.domain.model.Goal
import com.pplog.app.domain.model.UserProfile
import com.pplog.app.ui.components.PrimaryButton
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanBuilderScreen(
    navController: NavController,
    viewModel: PlanViewModel = koinViewModel()
) {
    val scope = rememberCoroutineScope()
    var goal by remember { mutableStateOf(Goal.STRENGTH) }
    var experience by remember { mutableStateOf(Experience.BEGINNER) }
    var equipment by remember { mutableStateOf(listOf(Equipment.BODYWEIGHT)) }
    var days by remember { mutableStateOf(3) }
    var minutes by remember { mutableStateOf(45) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Build Plan") },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text("Goal: ${goal.name}")
            // Simplified: in production use dropdowns and chip groups as in onboarding

            PrimaryButton(
                text = "Generate Plan",
                onClick = {
                    scope.launch {
                        val profile = UserProfile(
                            goal = goal,
                            experience = experience,
                            injuries = "",
                            equipment = equipment,
                            daysPerWeek = days,
                            minutesPerSession = minutes
                        )
                        viewModel.buildPlan(profile)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }
}
