/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.presentation

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * A simple clock interface to allow for dependency injection in tests.
 */
interface Clock {
    fun currentTimeMillis(): Long
}

/**
 * The default clock implementation that uses the real system time.
 */
private object SystemClock : Clock {
    override fun currentTimeMillis(): Long = System.currentTimeMillis()
}

class HighImpactViewModel(
    // Inject the clock. It defaults to the real system clock.
    private val clock: Clock = SystemClock
) : ViewModel() {

    // --- STATE PROPERTIES FOR THE UI ---
    val highImpactThreshold: MutableState<Int> = mutableIntStateOf(120) // Default threshold BPM
    val totalHighImpactTime: MutableState<Duration> = mutableStateOf(Duration.ZERO)

    // --- INTERNAL PROPERTIES FOR LOGIC ---
    private var highImpactStartTime: Long? = null
    private var lastProcessedHeartRate: Double = 0.0

    // For testing purposes, allow the test to replace the clock.
    @VisibleForTesting
    internal fun setTestClock(testClock: Clock) {
        // This function is not needed if we use constructor injection, but can be useful.
        // We will stick to constructor injection for simplicity.
    }

    /**
     * This is the entry point for data from the main DataViewModel.
     * It contains logic to prevent re-calculating for the same HR value.
     */
    fun updateHeartRate(currentHr: Double) {
        // Only process if the heart rate has actually changed
        if (currentHr == lastProcessedHeartRate) return
        lastProcessedHeartRate = currentHr

        val inHighImpactZone = currentHr >= highImpactThreshold.value

        if (inHighImpactZone && highImpactStartTime == null) {
            // User just entered the high impact zone, record the start time using the injected clock.
            highImpactStartTime = clock.currentTimeMillis()
        } else if (!inHighImpactZone && highImpactStartTime != null) {
            // User just left the high impact zone.
            val endTime = clock.currentTimeMillis() // Use the injected clock
            val durationMillis = endTime - highImpactStartTime!!

            // Add the calculated duration to the total accumulated time.
            totalHighImpactTime.value = totalHighImpactTime.value + durationMillis.milliseconds

            // Reset the start time, ready for the next entry into the zone.
            highImpactStartTime = null
        }
    }

    fun setThreshold(newThreshold: Int) {
        highImpactThreshold.value = newThreshold
    }
}

