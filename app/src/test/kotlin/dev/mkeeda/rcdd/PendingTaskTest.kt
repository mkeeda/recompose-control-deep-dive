package dev.mkeeda.rcdd

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
class PendingTaskTest {
    @Test
    fun runTaskFailingTest() = runComposeUiTest {
        lateinit var pendingTask: PendingTaskState
        var taskStarted = false
        var taskFinished = false

        setContent {
            pendingTask = pendingTask {
                taskStarted = true
                delay(3000)
                taskFinished = true
            }
        }

        mainClock.autoAdvance = false

        taskStarted shouldBe false
        mainClock.advanceTimeBy(3000)
        taskStarted shouldBe false // task is still pending

        pendingTask.isRunning = true
        mainClock.advanceTimeBy(1500) // advance time to half of total task duration (3000ms)
        taskStarted shouldBe true // task started [FAILED: still pending]
        taskFinished shouldBe false // but not finished

        mainClock.advanceTimeBy(1500) // time of task is over
        taskStarted shouldBe true // task started
        taskFinished shouldBe true // task finished
    }

    @Test
    fun runTaskSuccessfulTest() = runComposeUiTest {
        lateinit var pendingTask: PendingTaskState
        var taskStarted = false
        var taskFinished = false

        setContent {
            pendingTask = pendingTask {
                taskStarted = true
                delay(3000)
                taskFinished = true
            }
        }

        mainClock.autoAdvance = false

        taskStarted shouldBe false
        mainClock.advanceTimeBy(3000)
        taskStarted shouldBe false // task is still pending

        pendingTask.isRunning = true
        waitForIdle() // wait for all recomposition tasks to finish
        mainClock.advanceTimeByFrame() // run all coroutine tasks of recomposition
        mainClock.advanceTimeBy(1500) // advance time to half of total task duration (3000ms)
        taskStarted shouldBe true // task started
        taskFinished shouldBe false // but not finished

        mainClock.advanceTimeBy(1500) // time of task is over
        taskStarted shouldBe true // task started
        taskFinished shouldBe true // task finished
    }
}
