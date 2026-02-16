/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.presentation

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

interface BreakReminderAppContainer {
    val breakReminderViewModelFactory: ViewModelProvider.Factory
}

class DefaultBreakReminderAppContainer(private val context: Context) : BreakReminderAppContainer {
    private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by lazy {
        PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile("settings")
        }
    }

    override val breakReminderViewModelFactory: ViewModelProvider.Factory by lazy {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(BreakReminderViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return BreakReminderViewModel(dataStore) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
