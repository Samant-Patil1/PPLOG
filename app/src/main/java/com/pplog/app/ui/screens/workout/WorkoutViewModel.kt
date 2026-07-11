package com.pplog.app.ui.screens.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pplog.app.data.repository.PlanRepository
import com.pplog.app.domain.model.WorkoutPlan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class WorkoutViewModel(planRepository: PlanRepository) : ViewModel() {

    val activePlan: StateFlow<WorkoutPlan?> = planRepository.getActivePlan()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val _completedSets = MutableStateFlow<Map<String, Set<Int>>>(emptyMap())
    val completedSets: StateFlow<Map<String, Set<Int>>> = _completedSets

    fun toggleSet(exerciseId: String, setNumber: Int) {
        val current = _completedSets.value
        val exerciseSets = current[exerciseId] ?: emptySet()
        val updatedSets = if (setNumber in exerciseSets) {
            exerciseSets - setNumber
        } else {
            exerciseSets + setNumber
        }
        _completedSets.value = current + (exerciseId to updatedSets)
    }

    fun isSetCompleted(exerciseId: String, setNumber: Int): Boolean {
        return _completedSets.value[exerciseId]?.contains(setNumber) ?: false
    }
}
