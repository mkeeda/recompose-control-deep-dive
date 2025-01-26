package dev.mkeeda.rcdd

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AnimatedBox(
    durationMills: Int
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        var flag by remember { mutableStateOf(false) }
        AnimatedVisibility(
            visible = !flag,
            enter = slideInHorizontally(animationSpec = tween(durationMills)) { it },
            exit = slideOutHorizontally(animationSpec = tween(durationMills)) { -it }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red)
                    .testTag("red_box"),
            ) {}
        }

        AnimatedVisibility(
            visible = flag,
            enter = slideInHorizontally(animationSpec = tween(durationMills)) { it },
            exit = slideOutHorizontally(animationSpec = tween(durationMills)) { -it }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Green)
                    .testTag("green_box"),
            ) {}
        }

        Button(onClick = { flag = !flag }) {
            Text(text = "TOGGLE")
        }
    }
}

@Preview
@Composable
fun AnimatedBoxPreview() {
    AnimatedBox(durationMills = 1000)
}