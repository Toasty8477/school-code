/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text


data class GraphDataPoint(val x: Float, val y: Float, val xLabel: String)
@Composable
fun LineGraph(
    modifier: Modifier = Modifier,
    data: List<GraphDataPoint>,
    yAxisLabel: String,
    xAxisLabel: String
) {
    val graphColor = MaterialTheme.colors.primary
    val axisColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
    val textColor = MaterialTheme.colors.onSurface
    val avgLineColor = MaterialTheme.colors.secondary
    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = modifier) {
        // Define the layout and data ranges
        val paddingLeft = 80f
        val paddingBottom = 80f
        val paddingTop = 40f
        val graphWidth = size.width - paddingLeft
        val graphHeight = size.height - paddingBottom - paddingTop

        val yMin = 50f
        val yMax = 120f
        val yRange = yMax - yMin
        // The X-axis now goes from 0 to 12 as requested.
        val xMin = 0f
        val xMax = 12f
        val xRange = xMax - xMin

        // --- Draw Axis Lines and Labels ---
        val xAxisYPos = paddingTop + graphHeight
        drawLine(start = Offset(paddingLeft, paddingTop), end = Offset(paddingLeft, paddingTop + graphHeight), color = axisColor, strokeWidth = 2f)
        drawLine(start = Offset(paddingLeft, xAxisYPos), end = Offset(size.width, xAxisYPos), color = axisColor, strokeWidth = 2f)

        // Draw Y-Axis Markers
        val maxLabel = "%.0f".format(yMax)
        val maxLabelStyle = TextStyle(color = textColor, fontSize = 12.sp)
        val maxLabelWidth = textMeasurer.measure(maxLabel, style = maxLabelStyle).size.width
        drawText(textMeasurer, text = maxLabel, topLeft = Offset(paddingLeft - maxLabelWidth - 10, paddingTop - 10), style = maxLabelStyle)

        val minLabel = "%.0f".format(yMin)
        val minLabelStyle = TextStyle(color = textColor, fontSize = 12.sp)
        val minLabelWidth = textMeasurer.measure(minLabel, style = minLabelStyle).size.width
        drawText(textMeasurer, text = minLabel, topLeft = Offset(paddingLeft - minLabelWidth - 10, xAxisYPos - 10), style = minLabelStyle)

        // Draw Axis Titles
        drawContext.canvas.nativeCanvas.apply {
            save()
            rotate(-90f)
            translate(-(paddingTop + graphHeight / 2), 20f)
            drawText(yAxisLabel, 0f, 0f, android.graphics.Paint().apply { color = textColor.toArgb(); textSize = 28f; textAlign = android.graphics.Paint.Align.CENTER })
            restore()
        }
        val titleStyle = TextStyle(color = textColor, fontSize = 14.sp)
        drawText(textMeasurer, text = xAxisLabel, topLeft = Offset(paddingLeft + (graphWidth / 2) - (textMeasurer.measure(xAxisLabel, style = titleStyle).size.width / 2), xAxisYPos + 40f), style = titleStyle)

        // --- GRAPH DRAWING LOGIC ---
        if (data.isNotEmpty()) {
            val scaledPoints = data.map { point ->
                val xPosition = paddingLeft + ((point.x - xMin) / xRange * graphWidth)
                val yPosition = paddingTop + (graphHeight - ((point.y - yMin) / yRange * graphHeight).coerceIn(0f, graphHeight))
                Offset(xPosition, yPosition)
            }

            // Draw X-Axis label for each point
            data.forEach { point ->val xPosition = paddingLeft + ((point.x / xRange) * graphWidth) // Use point.x which is the index
                // --- MODIFY THIS LINE ---
                val label = point.xLabel // Get the label directly from the data point

                val textStyle = TextStyle(color = textColor, fontSize = 12.sp)
                val labelWidth = textMeasurer.measure(label, style = textStyle).size.width
                drawText(textMeasurer, text = label, topLeft = Offset(xPosition - (labelWidth / 2), xAxisYPos + 5f), style = textStyle)
            }

            // Draw a circle for every point
            scaledPoints.forEach { point ->
                drawCircle(color = graphColor, radius = 8f, center = point)
            }

            // Draw the connecting line
            if (scaledPoints.size > 1) {
                val sortedPoints = scaledPoints.sortedBy { it.x }
                val linePath = Path().apply {
                    moveTo(sortedPoints.first().x, sortedPoints.first().y)
                    for (i in 1 until sortedPoints.size) {
                        val p1 = sortedPoints[i - 1]
                        val p2 = sortedPoints[i]
                        val controlPoint1 = Offset((p1.x + p2.x) / 2f, p1.y)
                        val controlPoint2 = Offset((p1.x + p2.x) / 2f, p2.y)
                        cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y, p2.x, p2.y)
                    }
                }
                drawPath(path = linePath, color = graphColor, style = Stroke(width = 5f))
            }

            // Draw the overall average line
            val averageYValue = data.map { it.y }.average().toFloat()
            val avgYPosition = paddingTop + (graphHeight - (((averageYValue - yMin) / yRange) * graphHeight)).coerceIn(0f, graphHeight)
            drawLine(start = Offset(paddingLeft, avgYPosition), end = Offset(size.width, avgYPosition), color = avgLineColor, strokeWidth = 3f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))
            val avgLabel = "Avg: %.0f".format(averageYValue)
            drawText(textMeasurer, text = avgLabel, topLeft = Offset(paddingLeft + 5f, avgYPosition - 35f), style = TextStyle(color = avgLineColor, fontSize = 12.sp))
        }
    }
}

@Composable
fun GraphMainScreen(
    heartRateViewModel: HeartRateViewModel,
    onNavigateToGraph: () -> Unit
) {
    val currentHeartRate by heartRateViewModel.currentHeartRate.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Current BPM", style = MaterialTheme.typography.caption1)
        Text(
            text = if (currentHeartRate == 0) "--" else currentHeartRate.toString(),
            style = MaterialTheme.typography.display1,
            color = MaterialTheme.colors.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateToGraph, modifier = Modifier.size(ButtonDefaults.LargeButtonSize)) {
            Icon(imageVector = Icons.Default.ShowChart, contentDescription = "View graph")
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Button now has a more descriptive action and text
        Button(
            onClick = { heartRateViewModel.advanceToNextHour() },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface)
        ) {
            Text("Plot Next Hour")
        }
    }
}

@Composable
fun GraphScreen(
    heartRateViewModel: HeartRateViewModel,
    onNavigateBack: () -> Unit
) {

    val plottedData by heartRateViewModel.plottedPoints.collectAsState()
    // This now collects the correctly named `plottedPoints`
    val graphData = remember(plottedData) {
        plottedData.mapIndexed { index, dataPoint ->
            // Create the label e.g., "5am", "12pm"
            val amPm = if (dataPoint.isAm) "am" else "pm"
            val label = "${dataPoint.hourOfDay}${amPm}"
            // The X-value is now just the index (0, 1, 2...)
            GraphDataPoint(x = index.toFloat(), y = dataPoint.average, xLabel = label)
        }
    }
    val scrollState = rememberScrollState()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(scrollState)
        ) {
            val graphWidth = LocalConfiguration.current.screenWidthDp.dp * 2
            LineGraph(
                modifier = Modifier
                    .width(graphWidth)
                    .fillMaxHeight()
                    .padding(8.dp),
                data = graphData,
                yAxisLabel = "Heart Rate",
                xAxisLabel = "Time (hours)"
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Adds space between buttons
        ) {
            // The existing back button
            Button(
                onClick = onNavigateBack,
                modifier = Modifier.height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.surface.copy(
                        alpha = 0.8f
                    )
                )
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Go back to main screen"
                )
            }

            // The new reset button
            Button(
                onClick = { heartRateViewModel.resetGraphData() }, // Calls the new function
                modifier = Modifier.height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.error.copy(
                        alpha = 0.8f
                    )
                ) // Use error color for destructive action
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reset graph data")
            }
        }
    }
}