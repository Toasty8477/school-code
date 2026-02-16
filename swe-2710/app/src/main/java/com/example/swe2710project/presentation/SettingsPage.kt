/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.Text
import com.example.swe2710project.data.DataViewModel

@Composable
fun SettingsMainScreen(navController: NavHostController) {

    val listState = rememberScalingLazyListState()

    ScreenScaffold(
        scrollState = listState,
        contentPadding = PaddingValues(5.dp)
    ) {
        contentPadding ->
        ScalingLazyColumn(
            state = listState,
            contentPadding = contentPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            item {
                GenericChip(
                    label = "Alert Zones",
                    icon = Icons.Default.AddAlert,
                    onClick = { navController.navigate("setAlertZones") }
                )
            }
            item {
                GenericChip(
                    label = "High Impact",
                    icon = Icons.Default.MonitorHeart,
                    onClick = { navController.navigate("setHighImpactThreshold") }
                )
            }
            item {
                GenericChip(
                    label = "Enable Features",
                    icon = Icons.Default.Settings,
                    onClick = { navController.navigate("metricEnableScreen") }
                )
            }
            item {
                GenericChip(
                    label = "Break Reminder",
                    icon = Icons.Default.Info,
                    onClick = { navController.navigate("setBreakReminder") }
                )
            }
        }
    }

}

@Composable
fun MetricSettingsScreen(viewModel: DataViewModel) {

    val listState = rememberScalingLazyListState()

    val hrChecked by viewModel.hrEnabled.collectAsState()
    val vibrationChecked by viewModel.vibrationEnabled.collectAsState()

    ScreenScaffold(
        scrollState = listState,
        contentPadding = PaddingValues(10.dp)
    ) { contentPadding ->
        ScalingLazyColumn(
            state = listState,
            contentPadding = contentPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Spacer(modifier = Modifier.height(5.dp))
            }
            item {
                GenericToggleChip(
                    label = { Text("Heart Rate Tracking") },
                    icon = { Icons.Default.MonitorHeart },
                    checked = hrChecked,
                    onCheckedChange = { viewModel.toggleHrEnabled() }
                )
            }
            item {
                GenericToggleChip(
                    label = { Text("Vibration Tracking") },
                    icon = { Icons.Default.Vibration },
                    checked = vibrationChecked,
                    onCheckedChange = { viewModel.vibrationEnabled.value = it }
                )
            }
        }
    }
}