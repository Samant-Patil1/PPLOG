package com.pplog.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pplog.app.data.repository.PlanRepository
import com.pplog.app.domain.model.WorkoutPlan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(planRepository: PlanRepository) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = planRepository.getActivePlan()
        .map { plan ->
            HomeUiState(
                activePlan = plan,
                workoutsThisWeek = 0,
                currentStreakDays = 0
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState()
        )

    data class HomeUiState(
        val activePlan: WorkoutPlan? = null,
        val workoutsThisWeek: Int = 0,
        val currentStreakDays: Int = 0
    )
}
