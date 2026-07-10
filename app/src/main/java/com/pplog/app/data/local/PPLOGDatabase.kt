package com.pplog.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.pplog.app.data.local.dao.ExerciseDao
import com.pplog.app.data.local.dao.PlanDao
import com.pplog.app.data.local.dao.WorkoutLogDao
import com.pplog.app.data.local.entity.ExerciseEntity
import com.pplog.app.data.local.entity.PlanEntity
import com.pplog.app.data.local.entity.PlanExerciseEntity
import com.pplog.app.data.local.entity.WorkoutLogEntity
import com.pplog.app.domain.model.Difficulty
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Database(
    entities = [ExerciseEntity::class, PlanEntity::class, PlanExerciseEntity::class, WorkoutLogEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PPLOGDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun planDao(): PlanDao
    abstract fun workoutLogDao(): WorkoutLogDao
}

class Converters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromDifficulty(value: Difficulty): String = value.name

    @TypeConverter
    fun toDifficulty(value: String): Difficulty = Difficulty.valueOf(value)

    @TypeConverter
    fun fromStringList(value: String): List<String> =
        if (value.isBlank()) emptyList() else value.split(",")

    @TypeConverter
    fun toStringList(value: List<String>): String = value.joinToString(",")
}
