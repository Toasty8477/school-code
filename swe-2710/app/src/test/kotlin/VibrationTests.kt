/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.example.swe2710project.data.DataViewModel
import com.example.swe2710project.data.calculateVibrationExposure
import com.example.swe2710project.healthServices.ActiveDataCollection
import com.example.swe2710project.presentation.MainApplication
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class VibrationTests {

    lateinit var viewModel: DataViewModel

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<MainApplication>()
        viewModel = DataViewModel(ActiveDataCollection(context))
    }

    @Test
    fun exampleEquation() {
        val oneHour = 3600000000000
        val threeHours = 10800000000000
        val halfHour = 1800000000000

        val readings = mutableListOf(Pair(2.0, oneHour),
            Pair(3.5, threeHours),
            Pair(10.0, halfHour))

        Assert.assertEquals(3.4, calculateVibrationExposure(readings), 0.1)
    }
}