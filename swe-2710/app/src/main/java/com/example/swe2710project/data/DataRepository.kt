package com.example.swe2710project.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * A singleton repository to hold and share heart rate data between the
 * HeartRateService and the UI (via DataViewModel).
 */
object DataRepository {

    private val _hr = MutableStateFlow(0.0)
    val hr = _hr.asStateFlow()

    fun updateHr(newHr: Double) {
        _hr.value = newHr
    }
}
