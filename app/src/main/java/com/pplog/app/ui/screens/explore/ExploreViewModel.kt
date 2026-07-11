package com.pplog.app.ui.screens.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pplog.app.data.repository.ExerciseRepository
import com.pplog.app.domain.model.Difficulty
import com.pplog.app.domain.model.Equipment
import com.pplog.app.domain.model.Exercise
import com.pplog.app.domain.model.MuscleGroup
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ExploreViewModel(exerciseRepository: ExerciseRepository) : ViewModel() {

    private val muscleFilter = MutableStateFlow<MuscleGroup?>(null)
    private val equipmentFilter = MutableStateFlow<Equipment?>(null)
    private val difficultyFilter = MutableStateFlow<Difficulty?>(null)

    val uiState: StateFlow<ExploreUiState> = combine(
        exerciseRepository.getAllExercises(),
        muscleFilter,
        equipmentFilter,
        difficultyFilter
    ) { exercises, muscle, equipment, difficulty ->
        ExploreUiState(
            exercises = exercises.filter { ex ->
                (muscle == null || muscle in ex.primaryMuscleGroups) &&
                (equipment == null || equipment in ex.equipment) &&
                (difficulty == null || difficulty == ex.difficulty)
            },
            muscleFilter = muscle,
            equipmentFilter = equipment,
            difficultyFilter = difficulty
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ExploreUiState()
    )

    fun setMuscleFilter(muscle: MuscleGroup?) { muscleFilter.value = muscle }
    fun setEquipmentFilter(equipment: Equipment?) { equipmentFilter.value = equipment }
    fun setDifficultyFilter(difficulty: Difficulty?) { difficultyFilter.value = difficulty }

    data class ExploreUiState(
        val exercises: List<Exercise> = emptyList(),
        val muscleFilter: MuscleGroup? = null,
        val equipmentFilter: Equipment? = null,
        val difficultyFilter: Difficulty? = null
    )
}
