package com.pplog.app

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pplog.app.data.local.dao.ExerciseDao
import com.pplog.app.data.local.seed.ExerciseSeedData
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlanFlowTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        val exerciseDao = org.koin.core.context.GlobalContext.get().get<ExerciseDao>()
        runBlocking {
            if (exerciseDao.count() == 0) {
                exerciseDao.insertAll(ExerciseSeedData.getExercises())
            }
        }
    }

    @Test
    fun createPlan_navigatesToHome() {
        composeTestRule.onNodeWithText("Create Plan").performClick()
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Welcome back").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Welcome back").assertExists()
    }
}
