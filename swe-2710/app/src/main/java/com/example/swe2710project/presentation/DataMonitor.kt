/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.presentation

class DataMonitor {
    private var averageSpeed: Double
    private var currentBPM: Double
    private var distance: Double
    private var highImpactDuration: Double
    private var lastReading: Int
    private var sp02: Double
    private var steps: Int

    init {
        averageSpeed = 0.0
        currentBPM = 0.0
        distance = 0.0
        highImpactDuration = 0.0
        lastReading = 0
        sp02 = 0.0
        steps = 0
    }


    fun getBPM(): Double {
        return currentBPM
    }

    fun getDistance(): Double {
        return distance
    }


    fun getSp02(): Double {
        return sp02
    }

    fun setBPM(bpm: Double) {
        currentBPM = bpm
    }

    private fun retrieveMetrics(): List<Boolean> {
        return emptyList()
    }

    private fun setActiveTracking() {

    }

    fun calculateAverage(heartRates: List<Double>,): Double {
        var total: Double = 0.0
        var count: Int = 0
        for (hr in heartRates) {
            total += hr
            count++
        }
        return total / count
    }

}