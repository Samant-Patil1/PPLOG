package com.pplog.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pplog.app.ui.screens.explore.ExerciseDetailScreen
import com.pplog.app.ui.screens.explore.ExploreScreen
import com.pplog.app.ui.screens.home.HomeScreen
import com.pplog.app.ui.screens.onboarding.OnboardingScreen
import com.pplog.app.ui.screens.plan.PlanBuilderScreen
import com.pplog.app.ui.screens.plan.PlanScreen
import com.pplog.app.ui.screens.settings.SettingsScreen
import com.pplog.app.ui.screens.workout.WorkoutScreen

sealed class Screen(val route: String) {
    data object Onboarding : Screen("onboarding")
    data object Home : Screen("home")
    data object Explore : Screen("explore")
    data object ExerciseDetail : Screen("exercise_detail/{exerciseId}")
    data object Plan : Screen("plan")
    data object PlanBuilder : Screen("plan_builder")
    data object Workout : Screen("workout")
    data object Settings : Screen("settings")
}

@Composable
fun PPLOGNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Onboarding.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onPlanCreated = { navController.navigate(Screen.Home.route) { popUpTo(Screen.Onboarding.route) { inclusive = true } } }
            )
        }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Explore.route) { ExploreScreen(navController) }
        composable(Screen.ExerciseDetail.route) { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getString("exerciseId") ?: ""
            ExerciseDetailScreen(exerciseId, navController)
        }
        composable(Screen.Plan.route) { PlanScreen(navController) }
        composable(Screen.PlanBuilder.route) { PlanBuilderScreen(navController) }
        composable(Screen.Workout.route) { WorkoutScreen(navController) }
        composable(Screen.Settings.route) { SettingsScreen(navController) }
    }
}
