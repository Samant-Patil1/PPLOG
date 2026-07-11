package com.pplog.app.ui.screens.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pplog.app.data.repository.PlanRepository
import com.pplog.app.domain.model.WorkoutPlan
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

    private val _completedSets = mutableMapOf<String, MutableSet<Int>>()

    fun toggleSet(exerciseId: String, setNumber: Int) {
        val sets = _completedSets.getOrPut(exerciseId) { mutableSetOf() }
        if (sets.contains(setNumber)) sets.remove(setNumber) else sets.add(setNumber)
    }

    fun isSetCompleted(exerciseId: String, setNumber: Int): Boolean {
        return _completedSets[exerciseId]?.contains(setNumber) ?: false
    }
}
