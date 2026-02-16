/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HeartRateSettings(private val context: Context) {

    private val minHeartRateKey = intPreferencesKey("min_heart_rate")
    private val maxHeartRateKey = intPreferencesKey("max_heart_rate")

    val minHeartRate: Flow<Int> = context.dataStore.data.map {
        it[minHeartRateKey] ?: 60
    }

    val maxHeartRate: Flow<Int> = context.dataStore.data.map {
        it[maxHeartRateKey] ?: 100
    }

    suspend fun saveHeartRateZone(min: Int, max: Int) {
        context.dataStore.edit {
            it[minHeartRateKey] = min
            it[maxHeartRateKey] = max
        }
    }
}
