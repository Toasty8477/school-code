/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.presentation

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class BreakReminderViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test initial break interval is default`() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val dataStore = PreferenceDataStoreFactory.create(
            scope = this,
            produceFile = { context.preferencesDataStoreFile("test_settings_1") }
        )
        val viewModel = BreakReminderViewModel(dataStore)

        advanceUntilIdle()
        assertEquals(15, viewModel.breakInterval.value)
    }

    @Test
    fun `test set break interval updates value and saves to datastore`() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val dataStore = PreferenceDataStoreFactory.create(
            scope = this,
            produceFile = { context.preferencesDataStoreFile("test_settings_2") }
        )
        val viewModel = BreakReminderViewModel(dataStore)
        advanceUntilIdle()

        viewModel.setBreakInterval(30)
        advanceUntilIdle()

        assertEquals(30, viewModel.breakInterval.value)

        val newViewModel = BreakReminderViewModel(dataStore)
        advanceUntilIdle()
        assertEquals(30, newViewModel.breakInterval.value)
    }
    
    @Test
    fun `test show and hide confirmation`() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val dataStore = PreferenceDataStoreFactory.create(
            scope = this,
            produceFile = { context.preferencesDataStoreFile("test_settings_3") }
        )
        val viewModel = BreakReminderViewModel(dataStore)
        advanceUntilIdle()

        assertEquals(false, viewModel.showConfirmation.value)

        viewModel.setBreakInterval(20)
        advanceUntilIdle()
        assertEquals(true, viewModel.showConfirmation.value)

        viewModel.hideConfirmation()
        advanceUntilIdle()
        assertEquals(false, viewModel.showConfirmation.value)
    }
}
