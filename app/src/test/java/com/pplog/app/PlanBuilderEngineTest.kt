package com.pplog.app

import com.pplog.app.domain.engine.PlanBuilderEngine
import com.pplog.app.domain.model.Difficulty
import com.pplog.app.domain.model.Equipment
import com.pplog.app.domain.model.Exercise
import com.pplog.app.domain.model.Experience
import com.pplog.app.domain.model.Goal
import com.pplog.app.domain.model.MuscleGroup
import com.pplog.app.domain.model.UserProfile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PlanBuilderEngineTest {

    private val engine = PlanBuilderEngine()

    private val exercises = listOf(
        Exercise(
            id = "ex_squat",
            name = "Squat",
            primaryMuscleGroups = listOf(MuscleGroup.LEGS),
            equipment = listOf(Equipment.BARBELL),
            difficulty = Difficulty.INTERMEDIATE,
            instructions = listOf("Lower", "Stand")
        ),
        Exercise(
            id = "ex_pushup",
            name = "Push-Up",
            primaryMuscleGroups = listOf(MuscleGroup.CHEST),
            equipment = listOf(Equipment.BODYWEIGHT),
            difficulty = Difficulty.BEGINNER,
            instructions = listOf("Lower", "Push")
        )
    )

    @Test
    fun `buildPlan returns plan with correct days per week`() {
        val profile = UserProfile(
            goal = Goal.STRENGTH,
            experience = Experience.BEGINNER,
            injuries = "",
            equipment = listOf(Equipment.BARBELL, Equipment.BODYWEIGHT),
            daysPerWeek = 3,
            minutesPerSession = 45
        )
        val plan = engine.buildPlan(exercises, profile)
        assertEquals(3, plan.daysPerWeek)
        assertEquals(3, plan.days.size)
    }

    @Test
    fun `buildPlan filters out exercises requiring unavailable equipment`() {
        val profile = UserProfile(
            goal = Goal.STRENGTH,
            experience = Experience.BEGINNER,
            injuries = "",
            equipment = listOf(Equipment.BODYWEIGHT),
            daysPerWeek = 1,
            minutesPerSession = 30
        )
        val plan = engine.buildPlan(exercises, profile)
        val selectedIds = plan.days.flatMap { it.exercises }.map { it.exerciseId }
        assertTrue(selectedIds.contains("ex_pushup"))
        assertTrue(!selectedIds.contains("ex_squat"))
    }
}
