package com.pplog.app.data.local.seed

import com.pplog.app.data.local.entity.ExerciseEntity
import com.pplog.app.domain.model.Difficulty
import com.pplog.app.domain.model.Equipment
import com.pplog.app.domain.model.MuscleGroup

object ExerciseSeedData {
    fun getExercises(): List<ExerciseEntity> = listOf(
        ExerciseEntity(
            id = "ex_squat",
            name = "Barbell Back Squat",
            primaryMuscleGroups = MuscleGroup.LEGS.name,
            secondaryMuscleGroups = "${MuscleGroup.CORE.name},${MuscleGroup.CALVES.name}",
            equipment = Equipment.BARBELL.name,
            difficulty = Difficulty.INTERMEDIATE,
            instructions = "Stand with feet shoulder-width apart, barbell resting on upper traps.\nBrace core and lower hips back and down until thighs are parallel to floor.\nDrive through heels to stand, squeezing glutes at the top.",
            imageUrl = "exercises/barbell_squat.png",
            localImagePath = null,
            tips = "Keep chest up, knees tracking over toes, neutral spine throughout."
        ),
        ExerciseEntity(
            id = "ex_bench_press",
            name = "Barbell Bench Press",
            primaryMuscleGroups = MuscleGroup.CHEST.name,
            secondaryMuscleGroups = "${MuscleGroup.SHOULDERS.name},${MuscleGroup.TRICEPS.name}",
            equipment = Equipment.BARBELL.name,
            difficulty = Difficulty.INTERMEDIATE,
            instructions = "Lie flat on bench, eyes under bar, feet planted.\nGrip bar slightly wider than shoulders, unrack and hold over chest.\nLower to mid-chest with control, then press back to start.",
            imageUrl = "exercises/bench_press.png",
            localImagePath = null,
            tips = "Keep shoulder blades retracted, elbows tucked ~75 degrees."
        ),
        ExerciseEntity(
            id = "ex_deadlift",
            name = "Conventional Deadlift",
            primaryMuscleGroups = MuscleGroup.BACK.name,
            secondaryMuscleGroups = "${MuscleGroup.LEGS.name},${MuscleGroup.CORE.name}",
            equipment = Equipment.BARBELL.name,
            difficulty = Difficulty.ADVANCED,
            instructions = "Stand with feet hip-width apart, bar over mid-foot.\nHinge at hips and knees to grip the bar.\nBrace and stand by extending hips and knees together, bar close to body.",
            imageUrl = "exercises/deadlift.png",
            localImagePath = null,
            tips = "Keep a neutral spine; do not round lower back."
        ),
        ExerciseEntity(
            id = "ex_overhead_press",
            name = "Standing Overhead Press",
            primaryMuscleGroups = MuscleGroup.SHOULDERS.name,
            secondaryMuscleGroups = "${MuscleGroup.TRICEPS.name},${MuscleGroup.CORE.name}",
            equipment = Equipment.BARBELL.name,
            difficulty = Difficulty.INTERMEDIATE,
            instructions = "Stand with bar at shoulder height, grip just outside shoulders.\nBrace core and press bar straight up until arms lock out.\nLower under control to shoulders.",
            imageUrl = "exercises/overhead_press.png",
            localImagePath = null,
            tips = "Avoid excessive lower-back arch; squeeze glutes."
        ),
        ExerciseEntity(
            id = "ex_dumbbell_row",
            name = "Single-Arm Dumbbell Row",
            primaryMuscleGroups = MuscleGroup.BACK.name,
            secondaryMuscleGroups = MuscleGroup.BICEPS.name,
            equipment = Equipment.DUMBBELL.name,
            difficulty = Difficulty.BEGINNER,
            instructions = "Place one knee and hand on bench, other foot on floor.\nHold dumbbell in free hand, arm extended.\nPull dumbbell to hip, squeeze lat, lower with control.",
            imageUrl = "exercises/dumbbell_row.png",
            localImagePath = null,
            tips = "Keep back flat; pull elbow back, not out."
        ),
        ExerciseEntity(
            id = "ex_pushup",
            name = "Push-Up",
            primaryMuscleGroups = MuscleGroup.CHEST.name,
            secondaryMuscleGroups = "${MuscleGroup.TRICEPS.name},${MuscleGroup.SHOULDERS.name}",
            equipment = Equipment.BODYWEIGHT.name,
            difficulty = Difficulty.BEGINNER,
            instructions = "Start in plank, hands under shoulders.\nLower body until chest nearly touches floor.\nPush back up to plank.",
            imageUrl = "exercises/pushup.png",
            localImagePath = null,
            tips = "Keep body in a straight line; control the descent."
        ),
        ExerciseEntity(
            id = "ex_lunge",
            name = "Walking Dumbbell Lunge",
            primaryMuscleGroups = MuscleGroup.LEGS.name,
            secondaryMuscleGroups = MuscleGroup.CORE.name,
            equipment = Equipment.DUMBBELL.name,
            difficulty = Difficulty.INTERMEDIATE,
            instructions = "Hold dumbbells at sides, stand tall.\nStep forward into a lunge, back knee toward floor.\nPush through front heel to stand and repeat with other leg.",
            imageUrl = "exercises/lunge.png",
            localImagePath = null,
            tips = "Keep torso upright; front knee stays over ankle."
        ),
        ExerciseEntity(
            id = "ex_plank",
            name = "Plank",
            primaryMuscleGroups = MuscleGroup.CORE.name,
            secondaryMuscleGroups = "${MuscleGroup.SHOULDERS.name},${MuscleGroup.CORE.name}",
            equipment = Equipment.BODYWEIGHT.name,
            difficulty = Difficulty.BEGINNER,
            instructions = "Support body on forearms and toes, elbows under shoulders.\nKeep body straight from head to heels.\nHold for target time while breathing normally.",
            imageUrl = "exercises/plank.png",
            localImagePath = null,
            tips = "Do not let hips sag or pike up."
        ),
        ExerciseEntity(
            id = "ex_lat_pulldown",
            name = "Lat Pulldown",
            primaryMuscleGroups = MuscleGroup.BACK.name,
            secondaryMuscleGroups = MuscleGroup.BICEPS.name,
            equipment = Equipment.MACHINE.name,
            difficulty = Difficulty.BEGINNER,
            instructions = "Sit at lat pulldown station, thighs secured.\nGrip bar wider than shoulders, arms extended.\nPull bar to upper chest, squeezing shoulder blades together.",
            imageUrl = "exercises/lat_pulldown.png",
            localImagePath = null,
            tips = "Lean back slightly; avoid using momentum."
        ),
        ExerciseEntity(
            id = "ex_leg_press",
            name = "Leg Press",
            primaryMuscleGroups = MuscleGroup.LEGS.name,
            secondaryMuscleGroups = MuscleGroup.CORE.name,
            equipment = Equipment.MACHINE.name,
            difficulty = Difficulty.BEGINNER,
            instructions = "Sit in leg press machine, feet shoulder-width on platform.\nLower platform by bending knees toward 90 degrees.\nPress back up without locking knees.",
            imageUrl = "exercises/leg_press.png",
            localImagePath = null,
            tips = "Keep lower back flat against pad; do not lock knees."
        ),
        ExerciseEntity(
            id = "ex_kettlebell_swing",
            name = "Kettlebell Swing",
            primaryMuscleGroups = MuscleGroup.LEGS.name,
            secondaryMuscleGroups = "${MuscleGroup.BACK.name},${MuscleGroup.CORE.name}",
            equipment = Equipment.KETTLEBELL.name,
            difficulty = Difficulty.INTERMEDIATE,
            instructions = "Stand with feet wider than shoulders, kettlebell between legs.\nHinge hips back, then explosively extend hips to swing bell to chest height.\nLet bell fall back between legs and repeat.",
            imageUrl = "exercises/kettlebell_swing.png",
            localImagePath = null,
            tips = "Power comes from hip hinge, not arms; keep back neutral."
        ),
        ExerciseEntity(
            id = "ex_tricep_pushdown",
            name = "Tricep Pushdown",
            primaryMuscleGroups = MuscleGroup.TRICEPS.name,
            secondaryMuscleGroups = "",
            equipment = Equipment.CABLE.name,
            difficulty = Difficulty.BEGINNER,
            instructions = "Stand at cable station, elbows pinned to sides.\nPush bar/rope down until arms fully extend.\nReturn under control to start.",
            imageUrl = "exercises/tricep_pushdown.png",
            localImagePath = null,
            tips = "Keep elbows stationary; squeeze triceps at bottom."
        )
    )
}
