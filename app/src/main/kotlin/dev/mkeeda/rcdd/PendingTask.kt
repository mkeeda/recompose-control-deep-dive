package dev.mkeeda.rcdd

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Stable
class PendingTaskState {
    var isRunning by mutableStateOf(false)
}

@Composable
fun pendingTask(task: suspend () -> Unit): PendingTaskState {
    val state = remember(task) { PendingTaskState() }
    if (state.isRunning) {
        LaunchedEffect(Unit) {
            task()
        }
    }
    return state
}