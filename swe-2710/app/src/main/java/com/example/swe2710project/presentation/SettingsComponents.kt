/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
**/

package com.example.swe2710project.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Picker
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.rememberPickerState
import kotlin.math.max

@Composable
fun SettingsScreen(
    minHeartRate: Int,
    maxHeartRate: Int,
    onSetZone: (min: Int, max: Int) -> Unit
) {
    val heartRateRange = (40..220).toList()

    // Ensure initial values are valid
    val initialMin = minHeartRate.coerceIn(40, 219) // Max min is 219 so max can be 220
    val initialMax = max(maxHeartRate, initialMin + 1).coerceIn(41, 220)

    var tempMinHeartRate by remember { mutableIntStateOf(initialMin) }
    var tempMaxHeartRate by remember { mutableIntStateOf(initialMax) }

    // Create filtered ranges for each picker
    val minRange = remember(tempMaxHeartRate) {
        heartRateRange.filter { it <= tempMaxHeartRate - 1 }
    }
    val maxRange = remember(tempMinHeartRate) {
        heartRateRange.filter { it >= tempMinHeartRate + 1 }
    }

    val minPickerState = rememberPickerState(
        initialNumberOfOptions = minRange.size,
        initiallySelectedIndex = minRange.indexOf(tempMinHeartRate).coerceAtLeast(0)
    )
    val maxPickerState = rememberPickerState(
        initialNumberOfOptions = maxRange.size,
        initiallySelectedIndex = maxRange.indexOf(tempMaxHeartRate).coerceAtLeast(0)
    )

    // Update temp values when pickers change
    LaunchedEffect(minPickerState.selectedOptionIndex) {
        if (minPickerState.selectedOptionIndex < minRange.size) {
            tempMinHeartRate = minRange[minPickerState.selectedOptionIndex]
        }
    }

    LaunchedEffect(maxPickerState.selectedOptionIndex) {
        if (maxPickerState.selectedOptionIndex < maxRange.size) {
            tempMaxHeartRate = maxRange[maxPickerState.selectedOptionIndex]
        }
    }

    // When min changes, update the max picker range and scroll to current value
    LaunchedEffect(tempMinHeartRate) {
        val newMaxRange = heartRateRange.filter { it >= tempMinHeartRate + 1 }
        val currentMaxInNewRange = newMaxRange.indexOf(tempMaxHeartRate)

        if (currentMaxInNewRange >= 0) {
            // Update max picker to show new range
            maxPickerState.numberOfOptions = newMaxRange.size
            // Scroll to maintain current selection
            maxPickerState.scrollToOption(currentMaxInNewRange)
        } else {
            // Current max is not in new range (shouldn't happen with our logic)
            val newMax = tempMinHeartRate + 1
            tempMaxHeartRate = newMax.coerceAtMost(220)
        }
    }

    // When max changes, update the min picker range and scroll to current value
    LaunchedEffect(tempMaxHeartRate) {
        val newMinRange = heartRateRange.filter { it <= tempMaxHeartRate - 1 }
        val currentMinInNewRange = newMinRange.indexOf(tempMinHeartRate)

        if (currentMinInNewRange >= 0) {
            // Update min picker to show new range
            minPickerState.numberOfOptions = newMinRange.size
            // Scroll to maintain current selection
            minPickerState.scrollToOption(currentMinInNewRange)
        } else {
            // Current min is not in new range (shouldn't happen with our logic)
            val newMin = tempMaxHeartRate - 1
            tempMinHeartRate = newMin.coerceAtLeast(40)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Select Range")
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Picker(
                state = minPickerState,
                modifier = Modifier.weight(1f),
                contentDescription = { "${minRange.getOrNull(minPickerState.selectedOptionIndex) ?: tempMinHeartRate}" }
            ) {
                val value = minRange.getOrNull(it) ?: tempMinHeartRate
                Text(text = "$value", style = MaterialTheme.typography.displaySmall)
            }

            Text(
                text = "-",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Picker(
                state = maxPickerState,
                modifier = Modifier.weight(1f),
                contentDescription = { "${maxRange.getOrNull(maxPickerState.selectedOptionIndex) ?: tempMaxHeartRate}" }
            ) {
                val value = maxRange.getOrNull(it) ?: tempMaxHeartRate
                Text(text = "$value", style = MaterialTheme.typography.displaySmall)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { onSetZone(tempMinHeartRate, tempMaxHeartRate) }) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Set Zone"
            )
        }
    }
}