/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.presentation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BreakReminderViewModel(private val dataStore: DataStore<Preferences>) : ViewModel() {

    private val breakIntervalKey = intPreferencesKey("break_interval")

    val breakInterval: StateFlow<Int> = dataStore.data.map { preferences ->
        preferences[breakIntervalKey] ?: 15
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = 15
    )

    private val _showConfirmation = MutableStateFlow(false)
    val showConfirmation: StateFlow<Boolean> = _showConfirmation

    fun setBreakInterval(interval: Int) {
        viewModelScope.launch {
            dataStore.edit {
                it[breakIntervalKey] = interval
            }
            _showConfirmation.value = true
        }
    }

    fun hideConfirmation() {
        viewModelScope.launch {
            _showConfirmation.value = false
        }
    }
}