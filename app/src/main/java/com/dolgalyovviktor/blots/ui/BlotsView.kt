package com.dolgalyovviktor.blots.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
fun BlotsView(
    modifier: Modifier = Modifier,
    blotsCount: Int,
    maxBlotsCountOnScreen: Int,
    blotCurveCount: Int,
    maxBlotRadiusChange: Dp,
    animationDuration: Duration
) = Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
) {
    // Setup for animatable properties of each blot
    val scales = remember(blotsCount, maxBlotsCountOnScreen) {
        List(blotsCount) { Animatable(0.5f) }
    }
    val alphas = remember(blotsCount, maxBlotsCountOnScreen) {
        List(blotsCount) { Animatable(1f) }
    }
    val rotations = remember(blotsCount, maxBlotsCountOnScreen) {
        List(blotsCount) { Animatable(0f) }
    }
    val curveRadiiExtra = remember(blotsCount, maxBlotsCountOnScreen, blotsCount, blotCurveCount) {
        (0 until blotsCount).associateWith {
            List(blotCurveCount) { Animatable(0f) }
        }
    }

    // Configuration for animation durations and delays
    val rotationAnimationDuration = animationDuration * 3
    val blotCurveAnimationDuration = animationDuration / 6
    val delayBetweenAnimations = animationDuration / (blotsCount - maxBlotsCountOnScreen)

    // Screen dimensions for sizing blots
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp
    val sizeDp = minOf(screenWidthDp, screenHeightDp)

    if (maxBlotsCountOnScreen >= blotsCount) {
        // Ensuring that maxBlotsCountOnScreen does not exceed blotsCount
        CircularProgressIndicator(modifier = Modifier.size(sizeDp))
    } else {
        // Rendering each blot with its animations
        (0 until blotsCount).forEachIndexed { index, _ ->
            BlotView(
                modifier = Modifier
                    .size(sizeDp)
                    .graphicsLayer {
                        scaleX = scales[index].value
                        scaleY = scales[index].value
                        alpha = alphas[index].value
                        rotationZ = rotations[index].value
                    },
                curveRadiusExtra = curveRadiiExtra[index].orEmpty().map { anim -> anim.value }
            )
        }
        val animationKey = "$blotsCount$maxBlotsCountOnScreen$blotCurveCount$maxBlotRadiusChange"
        // Initializing animations
        InitBlotsAlphaAnimation(
            animatedValues = alphas,
            duration = animationDuration,
            delayBetweenAnimations = delayBetweenAnimations,
            key = animationKey
        )
        InitBlotsScaleAnimation(
            animatedValues = scales,
            duration = animationDuration,
            delayBetweenAnimations = delayBetweenAnimations,
            key = animationKey
        )
        InitBlotsRotationAnimation(
            animatedValues = rotations,
            duration = rotationAnimationDuration,
            key = animationKey
        )

        // Initializes curve radius animations for each blot, affecting the shape of the blots.
        InitBlotsCurveRadiiAnimation(
            curveRadiiExtra = curveRadiiExtra,
            maxDistanceChangePx = with(LocalDensity.current) { maxBlotRadiusChange.toPx() },
            curveAnimationDuration = blotCurveAnimationDuration,
            key = animationKey
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BlotsViewPreview() = BlotsView(
    blotsCount = 3,
    maxBlotsCountOnScreen = 2,
    blotCurveCount = 4,
    maxBlotRadiusChange = 10.dp,
    animationDuration = 5.seconds
)