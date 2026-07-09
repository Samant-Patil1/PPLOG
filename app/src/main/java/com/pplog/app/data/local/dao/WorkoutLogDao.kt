package com.pplog.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pplog.app.data.local.entity.WorkoutLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: WorkoutLogEntity)

    @Query("SELECT * FROM workout_logs ORDER BY date DESC")
    fun getAll(): Flow<List<WorkoutLogEntity>>

    @Query("SELECT * FROM workout_logs WHERE id = :id")
    suspend fun getById(id: String): WorkoutLogEntity?

    @Query("SELECT * FROM workout_logs WHERE date = :date LIMIT 1")
    suspend fun getByDate(date: String): WorkoutLogEntity?
}
