/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.util.concurrent.TimeUnit

enum class AlertState {
    LOW, HIGH, NONE
}

enum class VibrationPattern {
    LOW, HIGH, NONE
}

class HeartRateAlertViewModel : ViewModel() {
    val alertState = mutableStateOf(AlertState.NONE)
    val cooldownEndTime = mutableStateOf(0L)
    val vibrationPattern = mutableStateOf(VibrationPattern.NONE)
    var minHeartRate = 60
    var maxHeartRate = 100

    fun checkHeartRate(heartRate: Float) {
        if (System.currentTimeMillis() < cooldownEndTime.value) {
            return
        }

        if (heartRate > 0) { // Only check valid heart rate readings
            val isHeartRateNormal = heartRate >= minHeartRate && heartRate <= maxHeartRate

            if (isHeartRateNormal) {
                if (alertState.value != AlertState.NONE) {
                    alertState.value = AlertState.NONE
                    vibrationPattern.value = VibrationPattern.NONE
                }
            } else {
                if (heartRate < minHeartRate) {
                    if (alertState.value != AlertState.LOW) {
                        alertState.value = AlertState.LOW
                        vibrationPattern.value = VibrationPattern.LOW
                    }
                } else { // heartRate > maxHeartRate
                    if (alertState.value != AlertState.HIGH) {
                        alertState.value = AlertState.HIGH
                        vibrationPattern.value = VibrationPattern.HIGH
                    }
                }
            }
        } else { // heartRate <= 0
            if (alertState.value != AlertState.NONE) {
                alertState.value = AlertState.NONE
                vibrationPattern.value = VibrationPattern.NONE
            }
        }
    }

    fun clearAlert() {
        alertState.value = AlertState.NONE
        vibrationPattern.value = VibrationPattern.NONE
        cooldownEndTime.value = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)
    }

    fun triggerVibration(pattern: VibrationPattern) {
        vibrationPattern.value = pattern
    }
}
