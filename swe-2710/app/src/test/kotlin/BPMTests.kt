/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.example.swe2710project.data.DataViewModel
import com.example.swe2710project.healthServices.ActiveDataCollection
import com.example.swe2710project.presentation.MainApplication
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.time.LocalDate
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@RunWith(RobolectricTestRunner::class)
class BPMTestsKT {
    lateinit var viewModel: DataViewModel
    lateinit var day: LocalDate

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<MainApplication>()
        day = LocalDate.now()
        viewModel = DataViewModel(ActiveDataCollection(context))
    }

    @Test
    fun testAddingValues() {
        // Should start empty
        Assert.assertEquals(0, viewModel.oldHr.size)
        // Add a value, size should be 1
        viewModel.calculateAverageHr(65.0, viewModel.oldHr, 0.0, 0, day)
        Assert.assertEquals(1, viewModel.oldHr.size)
        // Add a bunch of random values, size should be 60
        val randomHr = List(59) { Random.nextInt(65, 90).toDouble() }
        for (hr in randomHr) {
            viewModel.calculateAverageHr(hr, viewModel.oldHr, 0.0, 0, day)
        }
        Assert.assertEquals(60, viewModel.oldHr.size)
        // Add another value, size should still be 60
        viewModel.calculateAverageHr(65.0, viewModel.oldHr, 0.0, 0, day)
        Assert.assertEquals(60, viewModel.oldHr.size)
        // Add a value from the same minute as the last one, value should not be added
        viewModel.calculateAverageHr(66.0, viewModel.oldHr, 65.0,
            TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()), day)
        Assert.assertEquals(65.0, viewModel.oldHr[59], 0.5)
        // Add a value that is from the same time but more than 10 bpm greater, value should be added
        viewModel.calculateAverageHr(79.0, viewModel.oldHr, 65.0,
            TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()), day)
        Assert.assertEquals(65.0, viewModel.oldHr[58], 0.5)
        Assert.assertEquals(79.0, viewModel.oldHr[59], 0.5)
        // Add a value that is from the next day, data should reset
        viewModel.calculateAverageHr(65.0, viewModel.oldHr, 65.0,
            0, day.plusDays(1))
        Assert.assertEquals(1, viewModel.oldHr.size)
        Assert.assertEquals(65.0, viewModel.oldHr[0], 0.5)
    }

    @Test
    fun averageCalculationTest() {
        val heartRates = mutableListOf<Double>()
        heartRates.add(85.0)
        heartRates.add(86.0)
        heartRates.add(87.0)
        heartRates.add(87.0)
        heartRates.add(88.0)
        heartRates.add(88.0)
        heartRates.add(89.0)
        heartRates.add(89.0)
        heartRates.add(90.0)
        Assert.assertEquals(87.9, viewModel.calculateAverageHr(90.0,
                heartRates, 0.0, 0, day)[0]);
    }

    @Test
    fun test() {
        viewModel.updateIndicatorProgress(80.0)
        viewModel.indicatorProgress
        viewModel.indicatorColor
    }
}