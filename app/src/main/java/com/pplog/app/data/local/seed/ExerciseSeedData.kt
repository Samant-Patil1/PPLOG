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
            instructions = "1. Stand with feet shoulder-width apart, barbell resting on upper traps.\n2. Brace core and lower hips back and down until thighs are parallel to floor.\n3. Drive through heels to stand, squeezing glutes at the top.",
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
            instructions = "1. Lie flat on bench, eyes under bar, feet planted.\n2. Grip bar slightly wider than shoulders, unrack and hold over chest.\n3. Lower to mid-chest with control, then press back to start.",
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
            instructions = "1. Stand with feet hip-width apart, bar over mid-foot.\n2. Hinge at hips and knees to grip the bar.\n3. Brace and stand by extending hips and knees together, bar close to body.",
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
            instructions = "1. Stand with bar at shoulder height, grip just outside shoulders.\n2. Brace core and press bar straight up until arms lock out.\n3. Lower under control to shoulders.",
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
            instructions = "1. Place one knee and hand on bench, other foot on floor.\n2. Hold dumbbell in free hand, arm extended.\n3. Pull dumbbell to hip, squeeze lat, lower with control.",
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
            instructions = "1. Start in plank, hands under shoulders.\n2. Lower body until chest nearly touches floor.\n3. Push back up to plank.",
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
            instructions = "1. Hold dumbbells at sides, stand tall.\n2. Step forward into a lunge, back knee toward floor.\n3. Push through front heel to stand and repeat with other leg.",
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
            instructions = "1. Support body on forearms and toes, elbows under shoulders.\n2. Keep body straight from head to heels.\n3. Hold for target time while breathing normally.",
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
            instructions = "1. Sit at lat pulldown station, thighs secured.\n2. Grip bar wider than shoulders, arms extended.\n3. Pull bar to upper chest, squeezing shoulder blades together.",
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
            instructions = "1. Sit in leg press machine, feet shoulder-width on platform.\n2. Lower platform by bending knees toward 90 degrees.\n3. Press back up without locking knees.",
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
            instructions = "1. Stand with feet wider than shoulders, kettlebell between legs.\n2. Hinge hips back, then explosively extend hips to swing bell to chest height.\n3. Let bell fall back between legs and repeat.",
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
            instructions = "1. Stand at cable station, elbows pinned to sides.\n2. Push bar/rope down until arms fully extend.\n3. Return under control to start.",
            imageUrl = "exercises/tricep_pushdown.png",
            localImagePath = null,
            tips = "Keep elbows stationary; squeeze triceps at bottom."
        )
    )
}
