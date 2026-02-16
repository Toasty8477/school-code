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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.Picker
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.rememberPickerState
import kotlin.time.Duration

@Composable
fun HighImpactScreen(viewModel: HighImpactViewModel) {
    val totalTime = viewModel.totalHighImpactTime.value


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // --- Time Display Section ---
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.title2,
            text = "High Impact Time"
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.display1,
            text = formatDuration(totalTime)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun HighImpactSettings(viewModel: HighImpactViewModel, navController: NavController) {

    val currentThreshold by viewModel.highImpactThreshold
    // Define a reasonable range for the picker, e.g., from 80 BPM to 180 BPM
    val pickerOptionsCount = 101 // (180 - 80 + 1)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // --- Threshold Picker Section ---
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Threshold",
            style = MaterialTheme.typography.caption1
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            val pickerState = rememberPickerState(
                initialNumberOfOptions = pickerOptionsCount,
                initiallySelectedIndex = (currentThreshold - 80).coerceIn(0, pickerOptionsCount - 1)
            )

            // Update the ViewModel whenever the picker selection changes
            LaunchedEffect(pickerState.selectedOptionIndex) {
                viewModel.setThreshold(pickerState.selectedOptionIndex + 80)
            }

            Picker(
                state = pickerState,
                modifier = Modifier.width(80.dp),
                verticalSpacing = 4.dp,
                contentDescription = { "${pickerState.selectedOptionIndex + 1}" }
            ) { optionIndex ->
                // The text displayed in the picker is the index + the starting BPM
                Text(
                    text = "${optionIndex + 80}",
                    style = MaterialTheme.typography.display2
                )
            }
            Text(
                text = "BPM",
                style = MaterialTheme.typography.title3,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { navController.popBackStack() }
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Confirm"
            )
        }
    }
}

/**
 * Formats a Duration object into a "HH:mm:ss" string.
 */
private fun formatDuration(duration: Duration): String {
    return duration.toComponents { hours, minutes, seconds, _ ->
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}


