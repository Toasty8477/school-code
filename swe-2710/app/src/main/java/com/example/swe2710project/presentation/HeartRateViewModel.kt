/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.TimeUnit


data class HourlyHeartRate(val hourOfDay: Int, val isAm: Boolean, val average: Float)

class HeartRateViewModel(application: Application) : AndroidViewModel(application) {

    //private var currentHour = 1
    private var hourlyTimer = Timer()

    // REMOVED: The old SensorManager is gone.

    private var collectionJob: Job? = null
    private val allReadings = mutableListOf<Pair<Long, Int>>()
    private var currentHourStartTime = System.currentTimeMillis()

    private val _currentHeartRate = MutableStateFlow(0)
    val currentHeartRate: StateFlow<Int> = _currentHeartRate

    private val _plottedPoints = MutableStateFlow<List<HourlyHeartRate>>(emptyList())
    val plottedPoints: StateFlow<List<HourlyHeartRate>> = _plottedPoints

    // ADD THIS INIT BLOCK
    init {
        startAutomaticPlotting()
    }

    // ADD THIS FUNCTION
    private fun startAutomaticPlotting() {
        // Ensure any existing timer is cancelled before starting a new one.
        hourlyTimer.cancel()
        hourlyTimer = Timer() // Create a new timer instance.

        // AUTOMATIC TRIGGER: Schedule the plotting task to run repeatedly.
        // For testing, you can change `TimeUnit.HOURS.toMillis(1)` to a shorter duration.
        val plotInterval = TimeUnit.HOURS.toMillis(1) // For testing every second
        // val plotInterval = TimeUnit.MINUTES.toMillis(1) // For testing every minute
        // val plotInterval = TimeUnit.HOURS.toMillis(1) // For production

        hourlyTimer.schedule(object : TimerTask() {
            override fun run() {
                viewModelScope.launch {
                    // When the timer fires, it plots the final average for the completed hour.
                    plotAverageForCurrentWindow(isFinalForHour = true)
                }
            }
        }, plotInterval, plotInterval)
    }

    private fun plotAverageForCurrentWindow(isFinalForHour: Boolean) {
        val readingsForWindow = allReadings.filter { it.first >= currentHourStartTime }
        val averageBpm = if (readingsForWindow.isNotEmpty()) {
            readingsForWindow.map { it.second }.average().toFloat()
        } else {
            0f
        }

        val calendar = java.util.Calendar.getInstance()
        var currentHourOfDay = calendar.get(java.util.Calendar.HOUR)
        if (currentHourOfDay == 0) currentHourOfDay = 12
        val isAm = calendar.get(java.util.Calendar.AM_PM) == java.util.Calendar.AM

        val newPoint = HourlyHeartRate(hourOfDay = currentHourOfDay, isAm = isAm, average = averageBpm)

        if (_plottedPoints.value.size >= 12) {
            _plottedPoints.value = listOf(newPoint)
        } else {
            _plottedPoints.value = _plottedPoints.value + newPoint
        }

        if (isFinalForHour) {
            currentHourStartTime = System.currentTimeMillis()
            allReadings.clear() // <-- ADD THIS LINE
        }
    }




    fun advanceToNextHour() {
        plotAverageForCurrentWindow(isFinalForHour = true)
    }

    fun resetGraphData() {
        hourlyTimer.cancel()
        allReadings.clear()
        _plottedPoints.value = emptyList()
        currentHourStartTime = System.currentTimeMillis()
        hourlyTimer = Timer()
        startAutomaticPlotting()
    }

    /**
     * NEW FUNCTION: This function will be called from MainActivity to feed the heart rate
     * data from the main DataViewModel into this graphing ViewModel.
     */
    fun updateHeartRate(newHeartRate: Double) {
        val heartRateInt = newHeartRate.toInt()
        _currentHeartRate.value = heartRateInt
        if (heartRateInt > 0) {
            allReadings.add(System.currentTimeMillis() to heartRateInt)
        }
    }

    // REMOVED: beginDataCollection() is no longer needed here, as MainActivity will control the flow.

    override fun onCleared() {
        super.onCleared()
        collectionJob?.cancel()
        hourlyTimer.cancel()
    }
}



