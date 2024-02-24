package com.dolgalyovviktor.blots.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.dolgalyovviktor.blots.common.FloatAnimatable
import com.dolgalyovviktor.blots.common.InitFloatAnimationLaunchedEffect
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.time.Duration

@Composable
fun InitBlotsAlphaAnimation(
    animatedValues: List<FloatAnimatable>,
    duration: Duration,
    delayBetweenAnimations: Duration,
    key: Any?
) = InitFloatAnimationLaunchedEffect(
    animatedValues = animatedValues,
    targetValue = 0f,
    duration = duration,
    delayBetweenAnimations = delayBetweenAnimations,
    key = key
)

@Composable
fun InitBlotsRotationAnimation(
    animatedValues: List<FloatAnimatable>,
    duration: Duration,
    key: Any?
) = animatedValues.forEach { animatable ->
    val directionMultiplier = if (Random.nextBoolean()) 1f else -1f
    InitFloatAnimationLaunchedEffect(
        animatedValues = listOf(animatable),
        targetValue = 360 * directionMultiplier,
        duration = duration,
        durationMultiplier = Random.nextDouble(0.75, 1.25),
        easing = LinearEasing,
        key = "$key${animatable.hashCode()}"
    )
}

@Composable
fun InitBlotsScaleAnimation(
    animatedValues: List<FloatAnimatable>,
    duration: Duration,
    delayBetweenAnimations: Duration,
    maxScale: Float = 1.5f,
    key: Any?
) = InitFloatAnimationLaunchedEffect(
    animatedValues = animatedValues,
    targetValue = maxScale,
    duration = duration,
    delayBetweenAnimations = delayBetweenAnimations,
    key = key
)

@Composable
fun InitBlotsCurveRadiiAnimation(
    curveRadiiExtra: Map<Int, List<FloatAnimatable>>,
    maxDistanceChangePx: Float,
    curveAnimationDuration: Duration,
    key: Any?
) = curveRadiiExtra.entries.forEach {
    InitBlotAnimations(
        distanceAnimations = it.value,
        maxDistanceChange = maxDistanceChangePx,
        animationDuration = curveAnimationDuration,
        key = key
    )
}

@Composable
private fun InitBlotAnimations(
    distanceAnimations: List<FloatAnimatable>,
    maxDistanceChange: Float,
    animationDuration: Duration,
    key: Any?
) = LaunchedEffect(key) {
    distanceAnimations.forEach { anim ->
        launch {
            repeat(Int.MAX_VALUE) {
                val randomRadius = Random.nextInt(0, 15)
                val distanceChange = Random.nextInt(
                    randomRadius,
                    (maxDistanceChange + randomRadius).roundToInt() + 1
                ).toFloat()
                anim.animateTo(
                    distanceChange,
                    tween(
                        animationDuration.inWholeMilliseconds.toInt(),
                        easing = FastOutSlowInEasing
                    )
                )
                anim.animateTo(
                    randomRadius.toFloat(),
                    tween(
                        animationDuration.inWholeMilliseconds.toInt() / 2,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        }
    }
}