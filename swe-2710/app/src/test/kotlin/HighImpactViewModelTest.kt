/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.swe2710project.presentation.Clock
import com.example.swe2710project.presentation.HighImpactViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

/**
 * Unit tests for the HighImpactViewModel.
 * This test class uses kotlinx-coroutines-test's virtual time dispatcher.
 */
@ExperimentalCoroutinesApi
class HighImpactViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HighImpactViewModel

    // Create a clock that uses the TestDispatcher's virtual time
    private val testClock = object : Clock {
        override fun currentTimeMillis(): Long {
            return testDispatcher.scheduler.currentTime
        }
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        // Create the ViewModel and pass our test clock to its constructor
        viewModel = HighImpactViewModel(clock = testClock)
    }

    @Test
    fun `high impact time is not accumulated when HR is below threshold`() = runTest {
        viewModel.updateHeartRate(100.0)
        advanceTimeBy(5000)
        viewModel.updateHeartRate(110.0)
        assertEquals("Time should not accumulate when HR is below threshold.", 0.seconds, viewModel.totalHighImpactTime.value)
    }

    @Test
    fun `high impact time is calculated correctly when HR crosses threshold`() = runTest {
        // --- Action 1: Enter high impact zone ---
        viewModel.updateHeartRate(130.0)
        advanceTimeBy(10_000)

        // --- Action 2: Leave high impact zone ---
        viewModel.updateHeartRate(110.0)

        // --- Verification 1 ---
        assertEquals("Incorrect duration after first high-impact session.", 10.seconds, viewModel.totalHighImpactTime.value)

        // --- Action 3: Re-enter high impact zone ---
        advanceTimeBy(5_000)
        viewModel.updateHeartRate(140.0)
        advanceTimeBy(5_000)

        // --- Action 4: Leave high impact zone again ---
        viewModel.updateHeartRate(100.0)

        // --- Verification 2 ---
        val expectedTotalTime = 15.seconds
        assertEquals("Total accumulated time is incorrect after multiple sessions.", expectedTotalTime, viewModel.totalHighImpactTime.value)
    }

    @Test
    fun `setThreshold changes the boundary for high impact calculation`() = runTest {
        val newThreshold = 100
        viewModel.setThreshold(newThreshold)
        assertEquals("Threshold should be updated.", newThreshold, viewModel.highImpactThreshold.value)

        viewModel.updateHeartRate(105.0)
        advanceTimeBy(7_000)
        viewModel.updateHeartRate(95.0)

        assertEquals("Time should be calculated based on the new threshold.", 7.seconds, viewModel.totalHighImpactTime.value)
    }

    @Test
    fun `no time is added if user stays in high impact zone without leaving`() = runTest {
        viewModel.updateHeartRate(130.0)
        advanceTimeBy(10_000)
        viewModel.updateHeartRate(135.0)
        advanceTimeBy(5_000)
        assertEquals("Time should not be added until leaving the high-impact zone.", 0.seconds, viewModel.totalHighImpactTime.value)
    }
}


