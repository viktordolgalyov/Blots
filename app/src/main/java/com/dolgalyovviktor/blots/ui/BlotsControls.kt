package com.dolgalyovviktor.blots.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dolgalyovviktor.blots.R
import com.dolgalyovviktor.blots.common.view.SliderControl
import kotlin.math.roundToInt

@Composable
fun BlotsControls(
    modifier: Modifier = Modifier,
    blotsCount: Int,
    maxBlotsCountOnScreen: Int,
    blotCurveCount: Int,
    maxBlotRadiusChange: Int,
    onBlotCountChanged: (Int) -> Unit,
    onMaxBlotsCountOnScreenChanged: (Int) -> Unit,
    onBlotCurveCountChanged: (Int) -> Unit,
    onMaxBlotRadiusChanged: (Int) -> Unit,
    blotCountValues: ClosedFloatingPointRange<Float>,
    maxBlotsOnScreenValues: ClosedFloatingPointRange<Float>,
    blotCurveCountValues: ClosedFloatingPointRange<Float>,
    maxBlotRadiusChangeValues: ClosedFloatingPointRange<Float>
) {
    // Temporary states for slider values to enable real-time adjustment without immediate state updates.
    var tempBlotsCount by remember(blotsCount) {
        mutableFloatStateOf(blotsCount.toFloat())
    }
    var tempMaxBlotsCountOnScreen by remember(blotsCount, maxBlotsCountOnScreen) {
        mutableFloatStateOf(maxBlotsCountOnScreen.toFloat())
    }
    var tempBlotCurveCount by remember(blotCurveCount) {
        mutableFloatStateOf(blotCurveCount.toFloat())
    }
    var tempMaxBlotRadiusChange by remember(maxBlotRadiusChange) {
        mutableFloatStateOf(maxBlotRadiusChange.toFloat())
    }

    // Adjust the max blots count on screen when the total blots count changes.
    LaunchedEffect(blotsCount) {
        val adjustedMaxCount = tempMaxBlotsCountOnScreen.coerceAtMost(blotsCount - 1f)
        tempMaxBlotsCountOnScreen = adjustedMaxCount
        onMaxBlotsCountOnScreenChanged(adjustedMaxCount.roundToInt())
    }
    Column(modifier = modifier.padding(16.dp)) {
        SliderControl(
            label = stringResource(
                R.string.template_label_blot_count,
                tempBlotsCount.toInt()
            ),
            value = tempBlotsCount,
            onValueChange = { tempBlotsCount = it },
            valueRange = blotCountValues,
            onValueChangeFinished = {
                onBlotCountChanged(tempBlotsCount.toInt())
            }
        )

        SliderControl(
            label = stringResource(
                R.string.template_label_max_on_screen,
                tempMaxBlotsCountOnScreen.toInt()
            ),
            value = tempMaxBlotsCountOnScreen,
            onValueChange = { tempMaxBlotsCountOnScreen = it },
            valueRange = maxBlotsOnScreenValues,
            onValueChangeFinished = {
                onMaxBlotsCountOnScreenChanged(tempMaxBlotsCountOnScreen.toInt())
            }
        )

        SliderControl(
            label = stringResource(
                R.string.template_label_blot_curve_count,
                tempBlotCurveCount.toInt()
            ),
            value = tempBlotCurveCount,
            onValueChange = { tempBlotCurveCount = it },
            valueRange = blotCurveCountValues,
            onValueChangeFinished = { onBlotCurveCountChanged(tempBlotCurveCount.toInt()) }
        )

        SliderControl(
            label = stringResource(
                R.string.template_label_max_blot_radius_change,
                tempMaxBlotRadiusChange.toInt()
            ),
            value = tempMaxBlotRadiusChange,
            onValueChange = { tempMaxBlotRadiusChange = it },
            valueRange = maxBlotRadiusChangeValues,
            onValueChangeFinished = { onMaxBlotRadiusChanged(tempMaxBlotRadiusChange.roundToInt()) }
        )
    }
}