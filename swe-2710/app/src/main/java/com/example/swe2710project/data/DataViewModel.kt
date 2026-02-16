/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.data

import android.app.Application
import android.content.Intent
import android.icu.util.Calendar
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.health.services.client.data.DataTypeAvailability
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.swe2710project.healthServices.ActiveDataCollection
import com.example.swe2710project.healthServices.HeartRateService
import com.example.swe2710project.presentation.IndicatorColors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.concurrent.TimeUnit


class DataViewModel(
    private val application: Application
) : ViewModel() {
    private val activeDataCollection = ActiveDataCollection(application)
    // Settings
    val hrEnabled: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val vibrationEnabled: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val stepsEnabled: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val distanceEnabled: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val speedEnabled: MutableStateFlow<Boolean> = MutableStateFlow(true)
    // Data
    val hr: MutableState<Double> = mutableDoubleStateOf(0.0)
    val avgHr: MutableState<Double> = mutableDoubleStateOf(0.0)
    val steps: MutableState<Long> = mutableLongStateOf(0L)
    val distance : MutableState<Double> = mutableDoubleStateOf(0.0)
    val avgSpeed : MutableState<Double> = mutableDoubleStateOf(0.0)
    var vibration: MutableState<Double> = mutableDoubleStateOf(0.0)
    var vibrationAlert: MutableState<Boolean> = mutableStateOf(false)
    val hrAvailability: MutableState<DataTypeAvailability> =
        mutableStateOf(DataTypeAvailability.UNKNOWN)
    val stepsAvailability: MutableState<DataTypeAvailability> =
        mutableStateOf(DataTypeAvailability.UNKNOWN)
    val distanceAvailability: MutableState<DataTypeAvailability> =
        mutableStateOf(DataTypeAvailability.UNKNOWN)

    var oldHr = mutableListOf<Double>()
    var indicatorProgress: MutableState<Float> = mutableFloatStateOf(0.5F)
    var indicatorColor: MutableState<Color> = mutableStateOf(IndicatorColors.NORMAL.stateColor)
    var lastHrReading = 0.0
    var lastHrReadingTime: Long = 0
    var day: LocalDate = LocalDate.now()

    init {
        viewModelScope.launch {
            val hrSupported = activeDataCollection.hasHeartRateCapability()
        }

        // ADD THIS NEW BLOCK INSIDE init { ... }
        viewModelScope.launch {
            DataRepository.hr.collect { newHr ->
                // Only update if there's a new, valid reading
                if (newHr > 0 && newHr != hr.value) {
                    hr.value = newHr
                    val averageData = calculateAverageHr(
                        hr = newHr,
                        oldHr = oldHr,
                        lastReading = lastHrReading,
                        lastReadingTime = lastHrReadingTime,
                        day = day
                    )
                    updateIndicatorProgress(hr = newHr)
                    avgHr.value = averageData[0] as Double
                    lastHrReadingTime = averageData[1] as Long
                    lastHrReading = averageData[2] as Double
                    day = LocalDate.now()
                }
            }
        }

    }
    fun calculateAverageHr(hr: Double,
                         oldHr: MutableList<Double>,
                         lastReading: Double,
                         lastReadingTime: Long,
                         day: LocalDate) : Array<Number> {
        val minute = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis())
        if (day != LocalDate.now()) {
            oldHr.clear()
            oldHr.add(hr)
        } else if (lastReadingTime < minute || hr > (lastReading + 10)) {
            oldHr.add(hr)
        }
        if (oldHr.size > 60) {
            oldHr.removeAt(1)
        }
        Log.d("Old HR", oldHr.toString())

        var total = 0.0
        var count = 0
        for (hr in oldHr) {
            total += hr
            count++
        }

        return arrayOf(total/count, minute, hr)
    }

    fun calculateAverageSpeed(distance: Double): Double {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return distance / hour
    }

    fun toggleHrEnabled() {
        hrEnabled.value = !hrEnabled.value
        if (hrEnabled.value) {
            // Start the service
            val intent = Intent(application, HeartRateService::class.java).apply {
                action = HeartRateService.ACTION_START
            }
            application.startService(intent)
        } else {
            // Stop the service
            val intent = Intent(application, HeartRateService::class.java).apply {
                action = HeartRateService.ACTION_STOP
            }
            application.startService(intent)
            // Reset UI values
            hr.value = 0.0
            hrAvailability.value = DataTypeAvailability.UNKNOWN
        }
    }


    fun updateIndicatorProgress(hr: Double) {
        val upperBound = 180
        val lowerBound = 10

        val mapped = (hr - lowerBound) / (upperBound - lowerBound)
        indicatorProgress.value = mapped.toFloat()
        if (mapped <= 0.2) {
            indicatorColor.value = IndicatorColors.VERY_LOW.stateColor
        } else if (mapped > 0.2 && mapped <= 0.4) {
            indicatorColor.value = IndicatorColors.LOW.stateColor
        } else if (mapped > 0.4 && mapped <= 0.6) {
            indicatorColor.value = IndicatorColors.NORMAL.stateColor
        } else if (mapped > 0.6 && mapped <= 0.8) {
            indicatorColor.value = IndicatorColors.HIGH.stateColor
        } else if (mapped > 0.8) {
            indicatorColor.value = IndicatorColors.VERY_HIGH.stateColor
        }
    }
}

class DataViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DataViewModel(
                application = application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
