package com.dolgalyovviktor.blots.common

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration

/**
 * Initializes floating point animations for a list of [FloatAnimatable] objects.
 *
 * @param animatedValues The list of [FloatAnimatable] objects to animate.
 * @param targetValue The target value for the animation.
 * @param duration The total duration of the animation.
 * @param delayBetweenAnimations The delay before starting each animation, allowing for staggered animations.
 * @param durationMultiplier A multiplier to adjust the animation's duration. Useful for speeding up or slowing down.
 * @param easing The easing function to apply to the animation for smoothness.
 * @param key An optional key to restart the animation if the key changes.
 * @param additionalAction An optional block to execute additional actions on each [FloatAnimatable] after animation.
 */
@Composable
fun InitFloatAnimationLaunchedEffect(
    animatedValues: List<FloatAnimatable>,
    targetValue: Float,
    duration: Duration,
    delayBetweenAnimations: Duration = Duration.ZERO,
    durationMultiplier: Double = 1.0,
    easing: Easing = FastOutSlowInEasing,
    key: Any? = null,
    additionalAction: suspend FloatAnimatable.() -> Unit = {}
) = LaunchedEffect(key) {
    animatedValues.forEachIndexed { index, animatable ->
        val delayTime = (delayBetweenAnimations * index).inWholeMilliseconds.toInt()
        launch {
            // Adding initial delay if specified, before starting the animation
            if (delayBetweenAnimations != Duration.ZERO) {
                delay(delayTime.toLong())
            }
            animatable.animateTo(
                targetValue = targetValue,
                animationSpec = infiniteRepeatable(
                    tween(
                        durationMillis = (duration.inWholeMilliseconds * durationMultiplier).toInt(),
                        easing = easing,
                        delayMillis = delayTime
                    )
                )
            )
            // Execute any additional specified actions on the animatable object.
            additionalAction(animatable)
        }
    }
}