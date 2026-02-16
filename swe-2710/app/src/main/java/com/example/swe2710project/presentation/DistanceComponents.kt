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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Speed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import com.example.swe2710project.data.DataViewModel

@Composable
fun DistanceScreen(viewModel: DataViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        GenericChip(
            label = "${viewModel.distance.value} Mi",
            icon = Icons.Default.PinDrop,
            modifier = Modifier.padding(5.dp, 2.5.dp).width(125.dp)
        )
        GenericChip(
            label = "${viewModel.steps.value} Steps",
            icon = Icons.AutoMirrored.Default.DirectionsWalk,
            modifier = Modifier.padding(5.dp, 2.5.dp).fillMaxWidth()
        )
        GenericChip(
            label = String.format("%02.1f MPH", viewModel.avgSpeed.value),
            icon = Icons.Default.Speed,
            modifier = Modifier.padding(5.dp, 2.5.dp).width(125.dp)
        )
    }
}

@Preview
@Composable
fun DistanceScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        GenericChip(
            label = "1.57 Mi",
            icon = Icons.Default.PinDrop,
            modifier = Modifier.padding(5.dp, 2.5.dp).width(125.dp)
        )
        GenericChip(
            label = "13263 Steps",
            icon = Icons.AutoMirrored.Default.DirectionsWalk,
            modifier = Modifier.padding(5.dp, 2.5.dp).fillMaxWidth()
        )
        GenericChip(
            label = "0.1 MPH",
            icon = Icons.Default.Speed,
            modifier = Modifier.padding(5.dp, 2.5.dp).width(125.dp)
        )
    }
}