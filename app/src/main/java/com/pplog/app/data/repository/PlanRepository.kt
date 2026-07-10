package com.pplog.app.data.repository

import com.pplog.app.data.local.dao.PlanDao
import com.pplog.app.data.local.entity.PlanEntity
import com.pplog.app.data.local.entity.PlanExerciseEntity
import com.pplog.app.domain.model.PlannedExercise
import com.pplog.app.domain.model.PlanDay
import com.pplog.app.domain.model.WorkoutPlan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.util.UUID

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class PlanRepository(private val planDao: PlanDao) {

    fun getActivePlan(): Flow<WorkoutPlan?> = combine(
        planDao.getActivePlan(),
        planDao.getActivePlan().map { it?.id }
    ) { planEntity, planId ->
        planEntity?.let { entity ->
            planId?.let { id ->
                planDao.getPlanExercises(id).map { exercises ->
                    entity.toDomain(exercises)
                }
            }
        }
    }.flatMapLatest { it ?: flowOf(null) }

    suspend fun savePlan(plan: WorkoutPlan, activate: Boolean = true) {
        val entity = PlanEntity(
            id = plan.id,
            name = plan.name,
            daysPerWeek = plan.daysPerWeek,
            isActive = activate
        )
        val exercises = plan.days.flatMap { day ->
            day.exercises.mapIndexed { index, ex ->
                PlanExerciseEntity(
                    id = UUID.randomUUID().toString(),
                    planId = plan.id,
                    dayNumber = day.dayNumber,
                    exerciseId = ex.exerciseId,
                    exerciseName = ex.exerciseName,
                    sets = ex.sets,
                    reps = ex.reps,
                    restSeconds = ex.restSeconds,
                    orderIndex = index
                )
            }
        }
        if (activate) {
            planDao.deactivateAllPlans()
        }
        planDao.insertPlanWithExercises(entity, exercises)
    }

    private fun PlanEntity.toDomain(exercises: List<PlanExerciseEntity>): WorkoutPlan {
        val grouped = exercises.groupBy { it.dayNumber }.toSortedMap()
        return WorkoutPlan(
            id = id,
            name = name,
            daysPerWeek = daysPerWeek,
            days = grouped.map { (dayNumber, list) ->
                PlanDay(
                    dayNumber = dayNumber,
                    focus = "Day $dayNumber",
                    exercises = list.sortedBy { it.orderIndex }.map { ex ->
                        PlannedExercise(
                            exerciseId = ex.exerciseId,
                            exerciseName = ex.exerciseName,
                            sets = ex.sets,
                            reps = ex.reps,
                            restSeconds = ex.restSeconds
                        )
                    }
                )
            }
        )
    }
}
