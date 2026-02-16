/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.presentation

import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HeartRateViewModelTest {

    @Test
    fun testSetHeartRateThreshold() {
        val viewModel = HeartRateAlertViewModel()
        assertEquals(AlertState.NONE, viewModel.alertState.value)

        viewModel.alertState.value = AlertState.HIGH
        assertEquals(AlertState.HIGH, viewModel.alertState.value)

        viewModel.alertState.value = AlertState.LOW
        assertEquals(AlertState.LOW, viewModel.alertState.value)
    }

    @Test
    fun testAlertStateChanges() {
        val viewModel = HeartRateAlertViewModel()
        viewModel.minHeartRate = 60
        viewModel.maxHeartRate = 100

        viewModel.checkHeartRate(50f)
        assertEquals(AlertState.LOW, viewModel.alertState.value)

        viewModel.checkHeartRate(110f)
        assertEquals(AlertState.HIGH, viewModel.alertState.value)

        viewModel.checkHeartRate(80f)
        assertEquals(AlertState.NONE, viewModel.alertState.value)
    }

    @Test
    fun testCooldownPeriod() {
        val viewModel = HeartRateAlertViewModel()
        viewModel.minHeartRate = 60
        viewModel.maxHeartRate = 100

        viewModel.checkHeartRate(50f)
        assertEquals(AlertState.LOW, viewModel.alertState.value)

        viewModel.clearAlert()
        assertEquals(AlertState.NONE, viewModel.alertState.value)
        assertTrue(System.currentTimeMillis() < viewModel.cooldownEndTime.value)

        viewModel.checkHeartRate(50f) // Should not trigger an alert during cooldown
        assertEquals(AlertState.NONE, viewModel.alertState.value)
    }

    @Test
    fun testHapticFeedback() {
        val viewModel = HeartRateAlertViewModel()
        viewModel.minHeartRate = 60
        viewModel.maxHeartRate = 100

        viewModel.checkHeartRate(50f)
        assertEquals(VibrationPattern.LOW, viewModel.vibrationPattern.value)

        viewModel.checkHeartRate(110f)
        assertEquals(VibrationPattern.HIGH, viewModel.vibrationPattern.value)

        viewModel.checkHeartRate(80f)
        assertNull(viewModel.vibrationPattern.value)
    }
}