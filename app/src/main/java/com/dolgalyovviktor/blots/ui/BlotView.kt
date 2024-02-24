package com.dolgalyovviktor.blots.ui

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.dolgalyovviktor.blots.R
import com.dolgalyovviktor.blots.common.radians
import kotlinx.coroutines.flow.combine
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun BlotView(
    modifier: Modifier = Modifier,
    curveRadiusExtra: List<Float>,
    gradientCenterColor: Color = colorResource(id = R.color.dark_purple),
    gradientEdgeColor: Color = colorResource(id = R.color.purple_700)
) {

    // Remember the maximum size of the Canvas to determine the radius.
    val maxSize = remember { mutableStateOf(Size.Zero) }

    // Calculate the base radius based on the minimum dimension of the Canvas.
    val baseRadius = remember(maxSize.value) {
        derivedStateOf { maxSize.value.minDimension / 4f }
    }
    // Prepare the gradient brush for painting the path.
    val gradientBrush = remember(maxSize.value) {
        val center = maxSize.value.center
        Brush.radialGradient(
            colors = listOf(gradientCenterColor, gradientEdgeColor),
            center = Offset(center.x, center.y),
            radius = (maxSize.value.minDimension + 1) / 2
        )
    }

    // State to hold the dynamically calculated path.
    val path = remember { mutableStateOf(Path()) }

    // Launch effect to recalculate the path whenever necessary parameters change.
    CalculateBlotLaunchedEffect(
        curveRadiusExtraList = curveRadiusExtra,
        baseRadius = baseRadius.value,
        maxSize = maxSize.value
    ) { updatedPath ->
        path.value = updatedPath
    }

    // Draw the path with the gradient brush.
    Canvas(modifier = modifier) {
        maxSize.value = this.size.copy()

        drawPath(
            brush = gradientBrush,
            path = path.value
        )
    }
}

@Composable
private fun CalculateBlotLaunchedEffect(
    curveRadiusExtraList: List<Float>,
    baseRadius: Float,
    maxSize: Size,
    onPathUpdated: (Path) -> Unit
) = LaunchedEffect(curveRadiusExtraList, baseRadius, maxSize) {
    // Combine snapshot flows to react to changes in any of the parameters.
    combine(
        snapshotFlow { curveRadiusExtraList },
        snapshotFlow { baseRadius },
        snapshotFlow { maxSize }
    ) { radiusExtra, radius, size ->
        val center = size.center
        // Generate a new path based on the current parameters.
        Path().apply {
            calculateBlotPath(
                target = this,
                center = center,
                baseRadius = radius,
                distances = radiusExtra.map { radius + it },
                curveCount = radiusExtra.size
            )
        }
    }.collect { updatedPath ->
        onPathUpdated(updatedPath)
    }
}

private fun calculateBlotPath(
    target: Path,
    center: Offset,
    baseRadius: Float,
    distances: List<Float>,
    curveCount: Int
) {
    target.reset()
    // Calculate the step for each segment of the path.
    val angleStep = 360f / curveCount
    val angles = List(curveCount) { index -> (angleStep * index).radians }

    // Construct the path by calculating points and control points for cubic Bezier curves.
    angles.forEachIndexed { index, angle ->
        val nextAngle = angles[(index + 1) % curveCount]

        val startPoint = center + Offset(cos(angle) * baseRadius, sin(angle) * baseRadius)
        val endPoint = center + Offset(cos(nextAngle) * baseRadius, sin(nextAngle) * baseRadius)

        val firstControlPoint = startPoint + Offset(
            (cos(angle - PI / 4) * distances[index]).toFloat(),
            (sin(angle - PI / 4) * distances[index]).toFloat()
        )
        val secondControlPoint = endPoint + Offset(
            (cos(nextAngle + PI / 4) * distances[(index + 1) % curveCount]).toFloat(),
            (sin(nextAngle + PI / 4) * distances[(index + 1) % curveCount]).toFloat()
        )

        if (index == 0) target.moveTo(startPoint.x, startPoint.y)
        target.cubicTo(
            x1 = firstControlPoint.x,
            y1 = firstControlPoint.y,
            x2 = secondControlPoint.x,
            y2 = secondControlPoint.y,
            x3 = endPoint.x,
            y3 = endPoint.y
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BlotViewPreview() = BlotView(
    curveRadiusExtra = (0 until 10).map { it.toFloat() }
)