package com.pplog.app.domain.engine

import com.pplog.app.domain.model.Difficulty
import com.pplog.app.domain.model.Equipment
import com.pplog.app.domain.model.Exercise
import com.pplog.app.domain.model.Experience
import com.pplog.app.domain.model.Goal
import com.pplog.app.domain.model.MuscleGroup
import com.pplog.app.domain.model.PlanDay
import com.pplog.app.domain.model.PlannedExercise
import com.pplog.app.domain.model.UserProfile
import com.pplog.app.domain.model.WorkoutPlan
import java.util.UUID

class PlanBuilderEngine {

    fun buildPlan(exercises: List<Exercise>, profile: UserProfile): WorkoutPlan {
        val filtered = exercises.filter { ex ->
            profile.equipment.any { it in ex.equipment }
        }.filter { ex ->
            val maxDifficulty = when (profile.experience) {
                Experience.BEGINNER -> Difficulty.INTERMEDIATE
                Experience.INTERMEDIATE -> Difficulty.ADVANCED
                Experience.ADVANCED -> Difficulty.ADVANCED
            }
            ex.difficulty.ordinal <= maxDifficulty.ordinal
        }

        val days = (1..profile.daysPerWeek).map { dayNumber ->
            val focus = focusForDay(dayNumber, profile.daysPerWeek)
            val candidates = filtered.filter { ex ->
                MuscleGroup.FULL_BODY in focus || ex.primaryMuscleGroups.any { it in focus }
            }.shuffled()
            val selected = candidates.take(exercisesPerDay(profile.minutesPerSession))
            PlanDay(
                dayNumber = dayNumber,
                focus = focus.joinToString(", ") { it.name.replace("_", " ") },
                exercises = selected.map { ex ->
                    PlannedExercise(
                        exerciseId = ex.id,
                        exerciseName = ex.name,
                        sets = setsForGoal(profile.goal),
                        reps = repsForGoal(profile.goal),
                        restSeconds = restForGoal(profile.goal)
                    )
                }
            )
        }

        return WorkoutPlan(
            id = UUID.randomUUID().toString(),
            name = "${profile.goal.name.lowercase().replaceFirstChar { it.uppercase() }} Plan",
            daysPerWeek = profile.daysPerWeek,
            days = days
        )
    }

    private fun focusForDay(day: Int, daysPerWeek: Int): List<MuscleGroup> {
        return when (daysPerWeek) {
            1, 2 -> listOf(MuscleGroup.FULL_BODY)
            3 -> when (day) {
                1 -> listOf(MuscleGroup.LEGS, MuscleGroup.CORE)
                2 -> listOf(MuscleGroup.CHEST, MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS)
                else -> listOf(MuscleGroup.BACK, MuscleGroup.BICEPS)
            }
            4 -> when (day) {
                1 -> listOf(MuscleGroup.CHEST, MuscleGroup.TRICEPS)
                2 -> listOf(MuscleGroup.BACK, MuscleGroup.BICEPS)
                3 -> listOf(MuscleGroup.LEGS, MuscleGroup.CORE)
                else -> listOf(MuscleGroup.SHOULDERS, MuscleGroup.CORE)
            }
            else -> when (day % 3) {
                1 -> listOf(MuscleGroup.LEGS)
                2 -> listOf(MuscleGroup.CHEST, MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS)
                else -> listOf(MuscleGroup.BACK, MuscleGroup.BICEPS)
            }
        }
    }

    private fun exercisesPerDay(minutes: Int): Int = when {
        minutes < 30 -> 4
        minutes < 60 -> 6
        else -> 8
    }

    private fun setsForGoal(goal: Goal): Int = when (goal) {
        Goal.STRENGTH -> 5
        Goal.HYPERTROPHY -> 4
        Goal.ENDURANCE -> 3
        Goal.FAT_LOSS -> 3
        Goal.MOBILITY -> 3
    }

    private fun repsForGoal(goal: Goal): String = when (goal) {
        Goal.STRENGTH -> "3-5"
        Goal.HYPERTROPHY -> "8-12"
        Goal.ENDURANCE -> "15-20"
        Goal.FAT_LOSS -> "12-15"
        Goal.MOBILITY -> "10-12"
    }

    private fun restForGoal(goal: Goal): Int = when (goal) {
        Goal.STRENGTH -> 180
        Goal.HYPERTROPHY -> 90
        Goal.ENDURANCE -> 45
        Goal.FAT_LOSS -> 60
        Goal.MOBILITY -> 60
    }
}
