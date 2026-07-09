package com.pplog.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_logs")
data class WorkoutLogEntity(
    @PrimaryKey val id: String,
    val planId: String,
    val dayNumber: Int,
    val date: String,
    val completedAt: String? = null,
    val exerciseLogsJson: String
)
