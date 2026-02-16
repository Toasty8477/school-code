/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material3.ProgressIndicatorDefaults
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.SegmentedCircularProgressIndicator
import com.example.swe2710project.R
import com.example.swe2710project.data.DataViewModel
import kotlin.math.roundToInt

@Composable
fun MainScreen(navController: NavController, viewModel: DataViewModel, highImpactViewModel: HighImpactViewModel) {

    val listState = rememberScalingLazyListState()
    val hr by viewModel.hr
    val indicatorState by viewModel.indicatorProgress
    val indicatorColor by viewModel.indicatorColor
    val highImpactTime by highImpactViewModel.totalHighImpactTime

    ScreenScaffold(
        scrollState = listState,
        contentPadding = PaddingValues(10.dp)
    ) {
        SegmentedCircularProgressIndicator(
            segmentCount = 5,
            progress = { indicatorState },
            startAngle = 300.0F,
            endAngle = 240.0F,
            modifier = Modifier.zIndex(1F)
                .padding(2.dp)
                .innerShadow(
                    shape = CircleShape,
                    shadow = Shadow(
                        radius = 10.dp,
                        spread = 6.dp,
                        color = Color(0x40000000)
                    )
                ),
            colors = ProgressIndicatorDefaults.colors().copy(
                indicatorColor = indicatorColor
            )
        )
        ScalingLazyColumn(
            state = listState,
            contentPadding = PaddingValues(
                top = 0.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 40.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { Spacer(modifier = Modifier.height(20.dp)) }
            item {
                Image(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    colorFilter = ColorFilter.tint(Color.White),
                    contentDescription = "Milwaukee Tool Logo",
                    painter = painterResource(R.drawable.milwaukee_logo)
                )
            }
             if(viewModel.hrEnabled.value) {
                 item {
                     GenericChip(
                         label = hr.roundToInt().toString(),
                         icon = Icons.Default.Favorite,
                         modifier = Modifier.fillMaxWidth(),
                         onClick = { navController.navigate("heartRate") }
                     )
                 }


            item {
                GenericChip(
                    label = highImpactTime.toComponents { hours, minutes, _, _ -> String.format("%01dh %02dm", hours, minutes) },
                    icon = Icons.Default.MonitorHeart,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { navController.navigate("highImpactScreen") }
                )
            }
             }
            if (viewModel.vibrationEnabled.value) {
                item {
                    GenericChip(
                        label = "${((viewModel.vibration.value / 5.0) * 100).roundToInt()}%",
                        icon = Icons.Default.Vibration,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navController.navigate("vibrationScreen") }
                    )
                }
            }

            item {
                GenericChip(
                    label = "Settings",
                    icon = Icons.Default.Settings,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { navController.navigate("settingsScreen") }
                )
            }
        }
    }
}