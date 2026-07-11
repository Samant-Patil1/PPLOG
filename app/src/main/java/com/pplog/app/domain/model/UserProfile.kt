package com.pplog.app.domain.model

data class UserProfile(
    val goal: Goal,
    val experience: Experience,
    val injuries: String,
    val equipment: List<Equipment>,
    val daysPerWeek: Int,
    val minutesPerSession: Int
)

enum class Goal { STRENGTH, HYPERTROPHY, FAT_LOSS, ENDURANCE, MOBILITY }
enum class Experience { BEGINNER, INTERMEDIATE, ADVANCED }
