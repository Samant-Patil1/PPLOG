package com.pplog.app

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlanFlowTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun createPlan_navigatesToHome() {
        composeTestRule.onNodeWithText("Create Plan").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Welcome back").assertExists()
    }
}
