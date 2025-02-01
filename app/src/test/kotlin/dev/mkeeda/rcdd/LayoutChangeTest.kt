package dev.mkeeda.rcdd

import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
class LayoutChangeTest {
    @Test
    fun successfulTest() = runComposeUiTest {
        var flag by mutableStateOf(false)
        setContent {
            if (flag) {
                Text("Hello")
            } else {
                Text("World")
            }
        }

        mainClock.autoAdvance = false

        onNodeWithText("Hello").assertDoesNotExist()
        onNodeWithText("World").assertExists()

        flag = true
        waitForIdle() // wait for all recomposition tasks to finish
        mainClock.advanceTimeByFrame() // run all coroutine tasks of recomposition
        onNodeWithText("Hello").assertExists()
        onNodeWithText("World").assertDoesNotExist()
    }
}
