package com.pplog.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pplog.app.domain.model.Difficulty

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey val id: String,
    val name: String,
    val primaryMuscleGroups: String,
    val secondaryMuscleGroups: String,
    val equipment: String,
    val difficulty: Difficulty,
    val instructions: String,
    val imageUrl: String? = null,
    val localImagePath: String? = null,
    val tips: String
)
