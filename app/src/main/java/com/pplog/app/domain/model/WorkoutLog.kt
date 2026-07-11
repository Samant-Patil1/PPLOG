package com.pplog.app.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class WorkoutLog(
    val id: String,
    val planId: String,
    val dayNumber: Int,
    val date: LocalDate,
    val completedAt: LocalDateTime? = null,
    val exerciseLogs: List<ExerciseLog> = emptyList()
)

data class ExerciseLog(
    val exerciseId: String,
    val exerciseName: String,
    val sets: List<SetLog> = emptyList()
)

data class SetLog(
    val setNumber: Int,
    val reps: Int? = null,
    val weightKg: Double? = null,
    val completed: Boolean = false
)
