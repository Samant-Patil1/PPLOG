package com.pplog.app.domain.model

data class WorkoutPlan(
    val id: String,
    val name: String,
    val daysPerWeek: Int,
    val days: List<PlanDay>
)

data class PlanDay(
    val dayNumber: Int,
    val focus: String,
    val exercises: List<PlannedExercise>
)

data class PlannedExercise(
    val exerciseId: String,
    val exerciseName: String,
    val sets: Int,
    val reps: String,
    val restSeconds: Int
)
