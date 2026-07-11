package com.pplog.app.di

import androidx.room.Room
import com.pplog.app.data.local.PPLOGDatabase
import com.pplog.app.data.repository.ExerciseRepository
import com.pplog.app.data.repository.PlanRepository
import com.pplog.app.data.repository.SettingsRepository
import com.pplog.app.ui.screens.explore.ExploreViewModel
import com.pplog.app.ui.screens.home.HomeViewModel
import com.pplog.app.ui.screens.onboarding.OnboardingViewModel
import com.pplog.app.ui.screens.plan.PlanViewModel
import com.pplog.app.ui.screens.settings.SettingsViewModel
import com.pplog.app.ui.screens.workout.WorkoutViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
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

    single { ExerciseRepository(get(), androidApplication()) }
    single { PlanRepository(get()) }
    single { SettingsRepository(androidApplication()) }

    viewModel { HomeViewModel(get()) }
    viewModel { ExploreViewModel(get()) }
    viewModel { OnboardingViewModel(get(), get()) }
    viewModel { PlanViewModel(get(), get()) }
    viewModel { WorkoutViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}
