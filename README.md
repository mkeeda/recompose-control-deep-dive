# Recompose control deep dive

## Overview

This repository contains sample tests demonstrating recomposition control using `MainTestClock` in Jetpack Compose. The tests cover various scenarios such as animation toggling, layout changes, and pending task execution.

### Tests Included

- **AnimatedBoxTest**: Tests the visibility of animated boxes during and after a toggle animation.
- **LayoutChangeTest**: Tests the recomposition behavior when a layout changes based on a state flag.
- **PendingTaskTest**: Tests the execution of a pending task with controlled time advancement.

### How to Run Tests

To run the tests, use the following Gradle command:

```sh
./gradlew testDebugUnitTest
```

or run the tests from Android Studio by right-clicking on the test class and selecting `Run`.
