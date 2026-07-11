package com.pplog.app.ui.screens.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pplog.app.data.repository.ExerciseRepository
import com.pplog.app.data.repository.PlanRepository
import com.pplog.app.domain.engine.PlanBuilderEngine
import com.pplog.app.domain.model.UserProfile
import com.pplog.app.domain.model.WorkoutPlan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PlanViewModel(
    private val planRepository: PlanRepository,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    val activePlan: StateFlow<WorkoutPlan?> = planRepository.getActivePlan()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val _builderState = MutableStateFlow(PlanBuilderUiState())
    val builderState: StateFlow<PlanBuilderUiState> = _builderState

    suspend fun buildPlan(profile: UserProfile): WorkoutPlan? {
        val exercises = exerciseRepository.getAllExercises().first()
        return PlanBuilderEngine().buildPlan(exercises, profile).also { plan ->
            planRepository.savePlan(plan, activate = true)
        }
    }

    data class PlanBuilderUiState(
        val isLoading: Boolean = false,
        val error: String? = null
    )
}
