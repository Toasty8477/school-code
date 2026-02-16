/**
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this
 file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.example.swe2710project.presentation.HeartRateViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Unit tests for the HeartRateViewModel, using Robolectric to provide an Android environment.
 * This class verifies the logic for plotting points, calculating averages, and resetting the graph data.
 */
@RunWith(RobolectricTestRunner::class) // Use Robolectric's test runner
@Config(sdk = [34])
class HeartRateViewModelTest {

    // This rule is needed for LiveData and other Architecture Components to work synchronously in tests.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HeartRateViewModel

    @Before
    fun setUp() {
        // Get the Application context from Robolectric
        val application = ApplicationProvider.getApplicationContext<Application>()
        // Re-create the ViewModel before each test with a real (but simulated) Application instance.
        viewModel = HeartRateViewModel(application)
    }

    @Test
    fun `updateHeartRate adds readings and calculates correct average`() {
        // --- Action ---
        // Simulate receiving heart rate data.
        viewModel.updateHeartRate(70.0)
        viewModel.updateHeartRate(80.0)

        // --- Verification ---
        viewModel.advanceToNextHour()
        val points = viewModel.plottedPoints.value

        Assert.assertNotNull("Plotted points list should not be null.", points)
        Assert.assertEquals("There should be exactly one point plotted.", 1, points.size)
        // The average should be (70 + 80) / 2 = 75.
        Assert.assertEquals(
            "The average BPM was not calculated correctly.",
            75.0f,
            points[0].average,
            0.01f
        )
    }

    @Test
    fun `advanceToNextHour plots one point with correct average`() {
        // --- Setup ---
        viewModel.updateHeartRate(60.0)
        viewModel.updateHeartRate(70.0)
        viewModel.updateHeartRate(80.0)

        // --- Action ---
        viewModel.advanceToNextHour()

        // --- Verification ---
        val points = viewModel.plottedPoints.value
        Assert.assertEquals("Should have plotted exactly one point.", 1, points.size)
        // Expected average: (60 + 70 + 80) / 3 = 70.
        Assert.assertEquals("The calculated average is incorrect.", 70.0f, points[0].average, 0.01f)
    }

    @Test
    fun `plotMultiplePoints adds points sequentially`() {
        // --- Action & Verification: First Point ---
        viewModel.updateHeartRate(100.0)
        viewModel.updateHeartRate(110.0)
        viewModel.advanceToNextHour() // Plots the first point, average = 105.

        var points = viewModel.plottedPoints.value
        Assert.assertEquals("Should be 1 point after first advance.", 1, points.size)
        Assert.assertEquals(105.0f, points[0].average, 0.01f)

        // --- Action & Verification: Second Point ---
        viewModel.updateHeartRate(50.0)
        viewModel.updateHeartRate(60.0)
        viewModel.updateHeartRate(70.0)
        viewModel.advanceToNextHour() // Plots the second point, average = 60.

        points = viewModel.plottedPoints.value
        Assert.assertEquals("Should be 2 points after second advance.", 2, points.size)
        Assert.assertEquals(
            "Average for the second point is incorrect.",
            60.0f,
            points[1].average,
            0.01f
        )
    }

    @Test
    fun `resetGraphData clears all plotted points`() {
        // --- Setup ---
        viewModel.updateHeartRate(80.0)
        viewModel.advanceToNextHour()
        Assert.assertEquals(
            "Pre-condition failed: A point should be plotted before reset.",
            1,
            viewModel.plottedPoints.value.size
        )

        // --- Action ---
        viewModel.resetGraphData()

        // --- Verification ---
        val pointsAfterReset = viewModel.plottedPoints.value
        Assert.assertTrue(
            "The plotted points list should be empty after reset.",
            pointsAfterReset.isEmpty()
        )
    }

    @Test
    fun `plotting thirteenth point resets the graph`() {
        // --- Setup ---
        // Plot 12 points quickly.
        for (i in 1..12) {
            viewModel.updateHeartRate(70.0 + i) // Use a different value each time
            viewModel.advanceToNextHour()
        }

        // --- Verification: Pre-condition ---
        Assert.assertEquals(
            "Should have 12 points before the final plot.",
            12,
            viewModel.plottedPoints.value.size
        )

        // --- Action ---
        // Plot the 13th point, which should trigger a reset.
        viewModel.updateHeartRate(100.0)
        viewModel.advanceToNextHour()

        // --- Verification: Post-condition ---
        val points = viewModel.plottedPoints.value
        Assert.assertEquals(
            "Graph should reset and contain only 1 point after plotting the 13th point.",
            1,
            points.size
        )
        // The single point should have the average of the last reading (100).
        Assert.assertEquals(
            "The new point after reset has an incorrect average.",
            100.0f,
            points[0].average,
            0.01f
        )
    }

    @Test
    fun `plotAverageForCurrentWindow calculates average BPM correctly`() {
        // --- Setup ---
        // Add a series of readings to the view model.
        viewModel.updateHeartRate(95.0) // FIX: Moved this to a new line
        viewModel.updateHeartRate(100.0)
        viewModel.updateHeartRate(105.0)

        // --- Action ---
        // Manually trigger the plotting of the next point.
        viewModel.advanceToNextHour()

        // --- Verification ---
        val points = viewModel.plottedPoints.value
        Assert.assertNotNull("Plotted points list should not be null.", points)
        Assert.assertEquals("There should be exactly one point plotted.", 1, points.size)

        // Expected average: (95 + 100 + 105) / 3 = 100.0f
        val expectedAverage = 100.0f
        val actualAverage = points[0].average
        Assert.assertEquals(
            "The calculated average BPM is incorrect.",
            expectedAverage,
            actualAverage,
            0.01f
        )
    }

    @Test
    fun `resetGraphData allows new points to be plotted after clearing`() {
        // --- Setup: Plot initial points ---
        viewModel.updateHeartRate(80.0)
        viewModel.updateHeartRate(85.0)
        viewModel.advanceToNextHour()
        Assert.assertEquals(
            "Pre-condition failed: Should have 1 point before reset.",
            1,
            viewModel.plottedPoints.value.size
        )

        // --- Action: Reset the data ---
        viewModel.resetGraphData()

        // --- Verification: Check that points are cleared ---
        var points = viewModel.plottedPoints.value
        Assert.assertTrue(
            "The plotted points list should be empty immediately after reset.",
            points.isEmpty()
        )

        // --- Action 2: Add a new point after the reset ---
        viewModel.updateHeartRate(60.0)
        viewModel.advanceToNextHour()

        // --- Verification 2: Check that the new point was added correctly ---
        points = viewModel.plottedPoints.value
        Assert.assertEquals(
            "Should have exactly one new point after resetting and plotting again.",
            1,
            points.size
        )
        Assert.assertEquals(
            "The average of the new point after reset is incorrect.",
            60.0f,
            points[0].average,
            0.01f
        )
    }


}