package com.pplog.app.di

import androidx.room.Room
import com.pplog.app.data.local.PPLOGDatabase
import com.pplog.app.data.repository.ExerciseRepository
import com.pplog.app.data.repository.PlanRepository
import com.pplog.app.data.repository.SettingsRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            PPLOGDatabase::class.java,
            "pplog_database"
        ).build()
    }
    single { get<PPLOGDatabase>().exerciseDao() }
    single { get<PPLOGDatabase>().planDao() }
    single { get<PPLOGDatabase>().workoutLogDao() }

    single { ExerciseRepository(get()) }
    single { PlanRepository(get()) }
    single { SettingsRepository(androidApplication()) }
}
