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
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.MaterialTheme
import com.example.swe2710project.data.DataViewModel
import java.text.DecimalFormat

@Composable
fun VibrationScreen(viewModel: DataViewModel) {

    val vibration by viewModel.vibration
    val dec = DecimalFormat("0.00")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GenericChip(
            label = "${dec.format(vibration)} m/s²",
            icon = Icons.Default.Vibration,
            modifier = Modifier.fillMaxWidth().padding(5.dp, 2.5.dp)
        )
        if (vibration >= 2.5 && vibration < 5.0) {
            GenericChip(
                label = "Take Action",
                icon = Icons.Default.Close,
                modifier = Modifier.fillMaxWidth().padding(5.dp, 2.5.dp),
                color = IndicatorColors.HIGH.stateColor
            )
        } else if (vibration >= 5.0) {
            GenericChip(
                label = "Unsafe Levels",
                icon = Icons.Default.AddAlert,
                modifier = Modifier.fillMaxWidth().padding(5.dp, 2.5.dp),
                color = IndicatorColors.VERY_HIGH.stateColor
            )
        } else {
            GenericChip(
                label = "Safe Level",
                icon = Icons.Default.Check,
                modifier = Modifier.fillMaxWidth().padding(5.dp, 2.5.dp),
                color = IndicatorColors.NORMAL.stateColor
            )
        }
    }
}