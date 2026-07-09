package com.pplog.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plans")
data class PlanEntity(
    @PrimaryKey val id: String,
    val name: String,
    val daysPerWeek: Int,
    val isActive: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
