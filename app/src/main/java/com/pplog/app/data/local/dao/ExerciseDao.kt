package com.pplog.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pplog.app.data.local.entity.ExerciseEntity
import com.pplog.app.domain.model.Difficulty
import com.pplog.app.domain.model.Equipment
import com.pplog.app.domain.model.MuscleGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises ORDER BY name")
    fun getAll(): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE id = :id")
    suspend fun getById(id: String): ExerciseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exercises: List<ExerciseEntity>)

    @Query("SELECT COUNT(*) FROM exercises")
    suspend fun count(): Int

    @Query("""
        SELECT * FROM exercises 
        WHERE primaryMuscleGroups LIKE '%' || :muscle || '%'
        ORDER BY name
    """)
    fun getByMuscleGroup(muscle: MuscleGroup): Flow<List<ExerciseEntity>>

    @Query("""
        SELECT * FROM exercises 
        WHERE equipment LIKE '%' || :equipment || '%'
        ORDER BY name
    """)
    fun getByEquipment(equipment: Equipment): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE difficulty = :difficulty ORDER BY name")
    fun getByDifficulty(difficulty: Difficulty): Flow<List<ExerciseEntity>>
}
