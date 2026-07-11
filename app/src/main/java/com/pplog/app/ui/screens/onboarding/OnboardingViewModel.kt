package com.pplog.app.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pplog.app.data.repository.ExerciseRepository
import com.pplog.app.data.repository.PlanRepository
import com.pplog.app.domain.engine.PlanBuilderEngine
import com.pplog.app.domain.model.Equipment
import com.pplog.app.domain.model.Experience
import com.pplog.app.domain.model.Goal
import com.pplog.app.domain.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val planRepository: PlanRepository,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState

    fun updateGoal(goal: Goal) {
        _uiState.value = _uiState.value.copy(goal = goal)
    }

    fun updateExperience(experience: Experience) {
        _uiState.value = _uiState.value.copy(experience = experience)
    }

    fun updateInjuries(injuries: String) {
        _uiState.value = _uiState.value.copy(injuries = injuries)
    }

    fun toggleEquipment(equipment: Equipment) {
        val current = _uiState.value.equipment.toMutableList()
        if (current.contains(equipment)) current.remove(equipment) else current.add(equipment)
        _uiState.value = _uiState.value.copy(equipment = current)
    }

    fun updateDaysPerWeek(days: Int) {
        _uiState.value = _uiState.value.copy(daysPerWeek = days)
    }

    fun updateMinutesPerSession(minutes: Int) {
        _uiState.value = _uiState.value.copy(minutesPerSession = minutes)
    }

    fun createPlan(onComplete: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            val profile = UserProfile(
                goal = state.goal,
                experience = state.experience,
                injuries = state.injuries,
                equipment = state.equipment,
                daysPerWeek = state.daysPerWeek,
                minutesPerSession = state.minutesPerSession
            )
            val exercises = exerciseRepository.getAllExercises().first()
            val plan = PlanBuilderEngine().buildPlan(exercises, profile)
            planRepository.savePlan(plan, activate = true)
            onComplete()
        }
    }

    data class OnboardingUiState(
        val goal: Goal = Goal.STRENGTH,
        val experience: Experience = Experience.BEGINNER,
        val injuries: String = "",
        val equipment: List<Equipment> = listOf(Equipment.BODYWEIGHT),
        val daysPerWeek: Int = 3,
        val minutesPerSession: Int = 45
    )
}
