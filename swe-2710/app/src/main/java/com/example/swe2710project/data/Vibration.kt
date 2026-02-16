/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.data

import kotlin.math.pow
import kotlin.math.sqrt

fun calculateVibrationExposure(readings: MutableList<Pair<Double, Long>>): Double {
    var totalVibration: Double

    val nanosInEightHours = 28_800_000_000_000
    var weightedAverage = 0.0
    // Add all terms to weighted average
    readings.forEach { e ->
        weightedAverage += e.first.pow(2) * e.second
    }

    totalVibration = sqrt((1/nanosInEightHours.toDouble())*weightedAverage)

    return totalVibration
}