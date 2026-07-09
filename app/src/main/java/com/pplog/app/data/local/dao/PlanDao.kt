package com.pplog.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.pplog.app.data.local.entity.PlanEntity
import com.pplog.app.data.local.entity.PlanExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlan(plan: PlanEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<PlanExerciseEntity>)

    @Transaction
    suspend fun insertPlanWithExercises(plan: PlanEntity, exercises: List<PlanExerciseEntity>) {
        insertPlan(plan)
        insertExercises(exercises)
    }

    @Query("SELECT * FROM plans WHERE isActive = 1 LIMIT 1")
    fun getActivePlan(): Flow<PlanEntity?>

    @Query("SELECT * FROM plans")
    fun getAllPlans(): Flow<List<PlanEntity>>

    @Query("SELECT * FROM plan_exercises WHERE planId = :planId ORDER BY dayNumber, orderIndex")
    fun getPlanExercises(planId: String): Flow<List<PlanExerciseEntity>>

    @Query("UPDATE plans SET isActive = 0")
    suspend fun deactivateAllPlans()

    @Query("UPDATE plans SET isActive = 1 WHERE id = :planId")
    suspend fun activatePlan(planId: String)

    @Query("DELETE FROM plans WHERE id = :planId")
    suspend fun deletePlan(planId: String)
}
