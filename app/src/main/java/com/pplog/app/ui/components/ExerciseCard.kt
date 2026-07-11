package com.pplog.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pplog.app.domain.model.Exercise

@Composable
fun ExerciseCard(
    exercise: Exercise,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Equipment: ${exercise.equipment.joinToString(", ") { it.name.replace("_", " ") }}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Target: ${exercise.primaryMuscleGroups.joinToString(", ") { it.name.replace("_", " ") }}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
