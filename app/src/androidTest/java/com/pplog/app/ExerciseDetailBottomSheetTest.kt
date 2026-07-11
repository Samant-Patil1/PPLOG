@file:OptIn(ExperimentalMaterial3Api::class)

package com.pplog.app

import androidx.activity.ComponentActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.pplog.app.data.local.dao.ExerciseDao
import com.pplog.app.data.local.seed.ExerciseSeedData
import com.pplog.app.ui.components.ExerciseDetailBottomSheet
import com.pplog.app.ui.theme.PPLOGTheme
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ExerciseDetailBottomSheetTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

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
    fun bottomSheetDisplaysExerciseNameAndSteps() {
        composeTestRule.setContent {
            PPLOGTheme {
                ExerciseDetailBottomSheet(
                    exerciseId = "ex_squat",
                    onDismiss = {},
                    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
                )
            }
        }

        composeTestRule.onNodeWithText("Barbell Back Squat").assertIsDisplayed()
        composeTestRule.onNodeWithText("Steps & Procedure").assertIsDisplayed()
        composeTestRule.onNodeWithText("1. Stand with feet shoulder-width apart, barbell resting on upper traps.").assertIsDisplayed()
        composeTestRule.onNodeWithText("Form Tips").assertIsDisplayed()
    }

    @Test
    fun bottomSheetDismissesOnClose() {
        var dismissed = false
        composeTestRule.setContent {
            PPLOGTheme {
                ExerciseDetailBottomSheet(
                    exerciseId = "ex_squat",
                    onDismiss = { dismissed = true },
                    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
                )
            }
        }

        composeTestRule.onNodeWithText("Close").performClick()
        assertTrue(dismissed)
    }
}
