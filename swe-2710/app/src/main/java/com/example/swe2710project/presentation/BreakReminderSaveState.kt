/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.presentation

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class AppContainer(private val context: Context) {
    val breakReminderViewModelFactory: ViewModelProvider.Factory =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(BreakReminderViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return BreakReminderViewModel(context.dataStore) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
}
