package com.dolgalyovviktor.blots.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.time.Duration.Companion.seconds

@Composable
fun BlotsApp(modifier: Modifier = Modifier) {
    // State variables to hold configuration of blots.
    var blotsCount by rememberSaveable { mutableIntStateOf(15) }
    var maxBlotsCountOnScreen by rememberSaveable { mutableIntStateOf(8) }
    var blotCurveCount by rememberSaveable { mutableIntStateOf(20) }
    var maxBlotRadiusChange by rememberSaveable { mutableIntStateOf(64) }
    val animationDuration = 5.seconds

    // Value ranges for configuration controls.
    val blotCountValues = 2f..25f
    val maxBlotsOnScreenValues = 1f..(blotsCount - 1).toFloat()
    val blotCurveCountValues = 3f..100f
    val maxBlotRadiusChangeValues = 0f..128f

    // Adjust layout based on orientation.
    val orientation = LocalConfiguration.current.orientation
    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Row(modifier = modifier) {
            // Blots view takes half the width in landscape mode.
            BlotsView(
                modifier = Modifier.fillMaxWidth(0.5f),
                blotsCount = blotsCount,
                maxBlotsCountOnScreen = maxBlotsCountOnScreen,
                blotCurveCount = blotCurveCount,
                maxBlotRadiusChange = maxBlotRadiusChange.dp,
                animationDuration = animationDuration
            )
            // Controls take the remaining width.
            BlotsControls(
                modifier = Modifier.fillMaxWidth(),
                blotsCount = blotsCount,
                maxBlotsCountOnScreen = maxBlotsCountOnScreen,
                blotCurveCount = blotCurveCount,
                maxBlotRadiusChange = maxBlotRadiusChange,
                onBlotCountChanged = { blotsCount = it },
                onMaxBlotsCountOnScreenChanged = { maxBlotsCountOnScreen = it },
                onBlotCurveCountChanged = { blotCurveCount = it },
                onMaxBlotRadiusChanged = { maxBlotRadiusChange = it },
                blotCountValues = blotCountValues,
                maxBlotsOnScreenValues = maxBlotsOnScreenValues,
                blotCurveCountValues = blotCurveCountValues,
                maxBlotRadiusChangeValues = maxBlotRadiusChangeValues
            )
        }
    } else {
        Column(modifier = modifier) {
            // In portrait mode, blots view and controls stack vertically.
            BlotsView(
                modifier = Modifier.fillMaxWidth(),
                blotsCount = blotsCount,
                maxBlotsCountOnScreen = maxBlotsCountOnScreen,
                blotCurveCount = blotCurveCount,
                maxBlotRadiusChange = maxBlotRadiusChange.dp,
                animationDuration = animationDuration
            )
            BlotsControls(
                modifier = Modifier.fillMaxWidth(),
                blotsCount = blotsCount,
                maxBlotsCountOnScreen = maxBlotsCountOnScreen,
                blotCurveCount = blotCurveCount,
                maxBlotRadiusChange = maxBlotRadiusChange,
                onBlotCountChanged = { blotsCount = it },
                onMaxBlotsCountOnScreenChanged = { maxBlotsCountOnScreen = it },
                onBlotCurveCountChanged = { blotCurveCount = it },
                onMaxBlotRadiusChanged = { maxBlotRadiusChange = it },
                blotCountValues = blotCountValues,
                maxBlotsOnScreenValues = maxBlotsOnScreenValues,
                blotCurveCountValues = blotCurveCountValues,
                maxBlotRadiusChangeValues = maxBlotRadiusChangeValues
            )
        }
    }
}

@Composable
@Preview(showBackground = true, device = "spec:parent=pixel_3a,orientation=landscape")
private fun BlotsAppPreviewLandscape() = Surface {
    BlotsApp(modifier = Modifier.fillMaxSize())
}

@Composable
@Preview(showBackground = true)
private fun BlotsAppPreviewPortrait() = Surface {
    BlotsApp(modifier = Modifier.fillMaxSize())
}