package com.pplog.app.ui.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pplog.app.domain.model.Equipment
import com.pplog.app.domain.model.Experience
import com.pplog.app.domain.model.Goal
import com.pplog.app.ui.components.HeroCard
import com.pplog.app.ui.components.PrimaryButton
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun OnboardingScreen(
    onPlanCreated: () -> Unit,
    viewModel: OnboardingViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var goalExpanded by remember { mutableStateOf(false) }
    var experienceExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeroCard(
            title = "Let's build your plan",
            subtitle = "Tell us a bit about your goals and equipment."
        )

        Text("Primary goal", style = MaterialTheme.typography.labelLarge)
        ExposedDropdownMenuBox(
            expanded = goalExpanded,
            onExpandedChange = { goalExpanded = it }
        ) {
            TextField(
                value = state.goal.name.lowercase().replaceFirstChar { it.uppercase() },
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = goalExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = goalExpanded,
                onDismissRequest = { goalExpanded = false }
            ) {
                Goal.entries.forEach { goal ->
                    DropdownMenuItem(
                        text = { Text(goal.name.lowercase().replaceFirstChar { it.uppercase() }) },
                        onClick = {
                            viewModel.updateGoal(goal)
                            goalExpanded = false
                        }
                    )
                }
            }
        }

        Text(
            "Experience",
            style = MaterialTheme.typography.labelLarge
        )
        ExposedDropdownMenuBox(
            expanded = experienceExpanded,
            onExpandedChange = { experienceExpanded = it }
        ) {
            TextField(
                value = state.experience.name.lowercase().replaceFirstChar { it.uppercase() },
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = experienceExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = experienceExpanded,
                onDismissRequest = { experienceExpanded = false }
            ) {
                Experience.entries.forEach { exp ->
                    DropdownMenuItem(
                        text = { Text(exp.name.lowercase().replaceFirstChar { it.uppercase() }) },
                        onClick = {
                            viewModel.updateExperience(exp)
                            experienceExpanded = false
                        }
                    )
                }
            }
        }

        Text(
            "Available equipment",
            style = MaterialTheme.typography.labelLarge
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Equipment.entries.forEach { equipment ->
                val selected = state.equipment.contains(equipment)
                FilterChip(
                    selected = selected,
                    onClick = { viewModel.toggleEquipment(equipment) },
                    label = { Text(equipment.name.replace("_", " ")) }
                )
            }
        }

        OutlinedTextField(
            value = state.injuries,
            onValueChange = viewModel::updateInjuries,
            label = { Text("Injuries or limitations (optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryButton(
            text = "Create Plan",
            onClick = { viewModel.createPlan(onPlanCreated) },
            enabled = state.equipment.isNotEmpty()
        )
    }
}
