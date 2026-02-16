/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.MaterialTheme
import com.example.swe2710project.data.DataViewModel
import kotlin.math.roundToInt

@Composable
fun HeartRateScreen(viewModel: DataViewModel, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GenericChip(
            label = "Current: ${viewModel.hr.value.roundToInt()}",
            icon = Icons.Default.Favorite,
            modifier = Modifier.padding(5.dp, 2.5.dp)
        )
        GenericChip(
            label = "Hourly Avg: ${viewModel.avgHr.value.roundToInt()}",
            icon = Icons.Default.MonitorHeart,
            modifier = Modifier.fillMaxWidth().padding(5.dp, 2.5.dp)
        )
        GenericChip(
            label = "Graph",
            icon = Icons.AutoMirrored.Default.ShowChart,
            modifier = Modifier.padding(5.dp, 2.5.dp),
            onClick = { navController.navigate("graph") },
        )
    }
}