package com.pplog.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "plan_exercises",
    foreignKeys = [
        ForeignKey(
            entity = PlanEntity::class,
            parentColumns = ["id"],
            childColumns = ["planId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("planId")]
)
data class PlanExerciseEntity(
    @PrimaryKey val id: String,
    val planId: String,
    val dayNumber: Int,
    val exerciseId: String,
    val exerciseName: String,
    val sets: Int,
    val reps: String,
    val restSeconds: Int,
    val orderIndex: Int
)
