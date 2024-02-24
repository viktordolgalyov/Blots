package com.dolgalyovviktor.blots.common.view

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dolgalyovviktor.blots.common.intStepsCount

@Composable
fun SliderControl(
    label: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: () -> Unit
) {
    Text(
        text = label,
        style = MaterialTheme.typography.titleMedium
    )
    Slider(
        value = value,
        onValueChange = onValueChange,
        valueRange = valueRange,
        steps = valueRange.intStepsCount.takeIf { it > 0 } ?: 1,
        onValueChangeFinished = onValueChangeFinished
    )
}

@Preview(showBackground = true)
@Composable
private fun SliderControlPreview() = SliderControl(
    label = "Label",
    value = 10f,
    valueRange = 0f..20f,
    onValueChange = {},
    onValueChangeFinished = {}
)