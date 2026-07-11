package com.pplog.app.domain.model

data class Exercise(
    val id: String,
    val name: String,
    val primaryMuscleGroups: List<MuscleGroup>,
    val secondaryMuscleGroups: List<MuscleGroup> = emptyList(),
    val equipment: List<Equipment>,
    val difficulty: Difficulty,
    val instructions: List<String>,
    val imageUrl: String? = null,
    val localImagePath: String? = null,
    val tips: List<String> = emptyList()
)

enum class Difficulty { BEGINNER, INTERMEDIATE, ADVANCED }
