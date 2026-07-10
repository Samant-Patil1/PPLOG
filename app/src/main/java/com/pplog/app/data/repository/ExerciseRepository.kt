package com.pplog.app.data.repository

import com.pplog.app.data.local.dao.ExerciseDao
import com.pplog.app.data.local.entity.ExerciseEntity
import com.pplog.app.domain.model.Difficulty
import com.pplog.app.domain.model.Equipment
import com.pplog.app.domain.model.Exercise
import com.pplog.app.domain.model.MuscleGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    fun getAllExercises(): Flow<List<Exercise>> = exerciseDao.getAll().map { list ->
        list.map { it.toDomain() }
    }

    suspend fun getExercise(id: String): Exercise? = exerciseDao.getById(id)?.toDomain()

    fun getExercisesByMuscleGroup(muscle: MuscleGroup): Flow<List<Exercise>> =
        exerciseDao.getByMuscleGroup(muscle).map { list -> list.map { it.toDomain() } }

    fun getExercisesByEquipment(equipment: Equipment): Flow<List<Exercise>> =
        exerciseDao.getByEquipment(equipment).map { list -> list.map { it.toDomain() } }

    fun getExercisesByDifficulty(difficulty: Difficulty): Flow<List<Exercise>> =
        exerciseDao.getByDifficulty(difficulty).map { list -> list.map { it.toDomain() } }

    private fun ExerciseEntity.toDomain(): Exercise = Exercise(
        id = id,
        name = name,
        primaryMuscleGroups = primaryMuscleGroups.split(",").map { MuscleGroup.valueOf(it) },
        secondaryMuscleGroups = secondaryMuscleGroups.split(",").filter { it.isNotBlank() }
            .map { MuscleGroup.valueOf(it) },
        equipment = equipment.split(",").map { Equipment.valueOf(it) },
        difficulty = difficulty,
        instructions = instructions.split("\n"),
        imageUrl = imageUrl,
        localImagePath = localImagePath,
        tips = tips.split(".").filter { it.isNotBlank() }
    )
}
