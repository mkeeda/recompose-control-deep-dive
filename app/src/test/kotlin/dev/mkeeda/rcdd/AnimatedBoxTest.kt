package dev.mkeeda.rcdd

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
class AnimatedBoxTest {
    @Test
    fun toggleFailingTest() = runComposeUiTest {
        setContent {
            AnimatedBox(durationMills = 3000)
        }

        mainClock.autoAdvance = false
        onNodeWithTag("red_box").assertIsDisplayed()
        onNodeWithTag("green_box").assertDoesNotExist()
        onNodeWithText("TOGGLE").performClick()

        mainClock.advanceTimeBy(1500) // advance time to half of total duration (3000ms)
        onNodeWithTag("green_box").assertIsDisplayed() // now both green and
        onNodeWithTag("red_box").assertIsDisplayed() // red should be visible

        mainClock.advanceTimeBy(1500) // finishing the animation
        onNodeWithTag("green_box").assertIsDisplayed() // now green should be visible
        onNodeWithTag("red_box").assertDoesNotExist() // but red shouldn't be [FAILED: still be visible]
    }

    @Test
    fun toggleSuccessfulTest() = runComposeUiTest {
        setContent {
            AnimatedBox(durationMills = 3000)
        }

        mainClock.autoAdvance = false
        onNodeWithTag("red_box").assertIsDisplayed()
        onNodeWithTag("green_box").assertDoesNotExist()
        onNodeWithText("TOGGLE").performClick()

        mainClock.advanceTimeByFrame() // send frame time to animation and trigger recomposition
        // await layout pass to set up animation,
        // but this example has no effects in drawing phase so this line is actually unnecessary.
        waitForIdle()

        mainClock.advanceTimeByFrame() // give animation a start time
        mainClock.advanceTimeBy(1500) // advance time to half of total duration (3000ms)
        onNodeWithTag("green_box").assertIsDisplayed() // now both green and
        onNodeWithTag("red_box").assertIsDisplayed() // red should be visible

        mainClock.advanceTimeBy(1500) // finishing the animation
        onNodeWithTag("green_box").assertIsDisplayed() // now green should be visible
        onNodeWithTag("red_box").assertDoesNotExist() // but red shouldn't be
    }
}