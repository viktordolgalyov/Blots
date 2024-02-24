package com.dolgalyovviktor.blots.common

import kotlin.math.roundToInt

val Float.radians
    get() = Math.toRadians(this.toDouble()).toFloat()

val ClosedFloatingPointRange<Float>.intStepsCount
    get() = (this.endInclusive - this.start).roundToInt() - 1