/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Picker
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.rememberPickerState

@Composable
fun BreakReminderScreen(
    breakInterval: Int,
    showConfirmation: Boolean,
    onSetBreakInterval: (interval: Int) -> Unit,
    onHideConfirmation: () -> Unit
) {
    var tempBreakInterval by remember { mutableIntStateOf(breakInterval) }

    if (showConfirmation) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Success",
                modifier = Modifier.height(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Reminder in $breakInterval min",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onHideConfirmation,
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("OK")
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Title section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(0.2f)
            ) {
                Text(
                    text = "Break Interval",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "(minutes)",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Picker section
            Column(
                modifier = Modifier.weight(0.6f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val pickerState = rememberPickerState(
                    initialNumberOfOptions = 60,
                    initiallySelectedIndex = tempBreakInterval - 1
                )

                LaunchedEffect(pickerState.selectedOptionIndex) {
                    tempBreakInterval = pickerState.selectedOptionIndex + 1
                }

                Picker(
                    state = pickerState,
                    modifier = Modifier.height(80.dp), // Limit picker height
                    verticalSpacing = 2.dp,
                    contentDescription = { "Break interval picker" }
                ) {
                    Text(
                        text = "${it + 1}",
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Button section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(0.2f)
            ) {
                Button(
                    onClick = { onSetBreakInterval(tempBreakInterval) },
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Set Break Interval"
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text("Set")
                }
            }
        }
    }
}
