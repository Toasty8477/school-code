/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.presentation

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.swe2710project.healthServices.ActiveDataCollection

class MainApplication : Application() {

    lateinit var appContainer: BreakReminderAppContainer
    lateinit var activeDataCollection: ActiveDataCollection
    lateinit var heartRateAlertViewModel: HeartRateAlertViewModel

    override fun onCreate() {
        super.onCreate()
        appContainer = DefaultBreakReminderAppContainer(this)
        activeDataCollection = ActiveDataCollection(this)
        heartRateAlertViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this).create(HeartRateAlertViewModel::class.java)
    }

    fun triggerVibration(pattern: VibrationPattern) {
        heartRateAlertViewModel.triggerVibration(pattern)
    }
}
