/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.presentation

import android.Manifest
import android.R.style.Theme_DeviceDefault
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.TimeText
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.swe2710project.data.DataViewModel
import com.example.swe2710project.data.DataViewModelFactory
import com.example.swe2710project.data.calculateVibrationExposure
import com.example.swe2710project.healthServices.ActiveDataCollection
import com.example.swe2710project.healthServices.HeartRateService
import com.example.swe2710project.presentation.theme.SWE2710ProjectTheme
import com.squareup.wire.internal.newMutableList
import java.util.concurrent.TimeUnit
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(Theme_DeviceDefault)

        val appContainer = (application as MainApplication).appContainer
        val intent = Intent(application, HeartRateService::class.java).apply {
            action = HeartRateService.ACTION_START
        }
        application.startService(intent)
        setContent {
            WearApp(appContainer.breakReminderViewModelFactory, (application as MainApplication).activeDataCollection)
        }
    }
}

enum class IndicatorColors(val stateColor: Color) {
    VERY_LOW(Color.hsl(264.0F, 0.57F, 0.6F)),
    LOW(Color.hsl(228.0F, 0.57F, 0.6F)),
    NORMAL(Color.hsl(112.0F, 0.57F, 0.6F)),
    HIGH(Color.hsl(31.0F, 0.78F, 0.55F)),
    VERY_HIGH(Color.hsl(7.0F, 0.57F, 0.6F))
}

@Composable
fun WearApp(
    breakReminderViewModelFactory: androidx.lifecycle.ViewModelProvider.Factory,
    activeDataCollection: ActiveDataCollection
) {
    val context = LocalContext.current

    val viewModel: DataViewModel = viewModel(
        factory = DataViewModelFactory(
            context.applicationContext as Application
        )
    )

    val highImpactViewModel: HighImpactViewModel = viewModel()
    val breakReminderViewModel: BreakReminderViewModel = viewModel(factory = breakReminderViewModelFactory)

    val hr by viewModel.hr
    val vibration by viewModel.vibration

    val navController = rememberSwipeDismissableNavController()
    SWE2710ProjectTheme {
        var minHeartRate by remember { mutableIntStateOf(60) }
        var maxHeartRate by remember { mutableIntStateOf(140) }
        val heartRateAlertViewModel: HeartRateAlertViewModel = viewModel()
        val heartRateViewModel: HeartRateViewModel = viewModel()
        val vibrator = remember { context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }

        heartRateAlertViewModel.minHeartRate = minHeartRate
        heartRateAlertViewModel.maxHeartRate = maxHeartRate

        // State to track if permissions are granted
        var vitalsPermissionGranted by remember { mutableStateOf(ContextCompat.checkSelfPermission(context, Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED) }
        var notificationsPermissionGranted by remember { mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
            } else {
                true // Notifications permission not needed for older versions
            }
        ) }

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            vitalsPermissionGranted = permissions[Manifest.permission.BODY_SENSORS] ?: vitalsPermissionGranted
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                notificationsPermissionGranted = permissions[Manifest.permission.POST_NOTIFICATIONS] ?: notificationsPermissionGranted
            }

            if (!notificationsPermissionGranted) {
                breakReminderViewModel.setBreakInterval(0) // Disable break reminder
            }
        }
        
        fun requestPermissions() {
            val permissionsToRequest = mutableListOf<String>()
            if (!vitalsPermissionGranted) {
                permissionsToRequest.add(Manifest.permission.BODY_SENSORS)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !notificationsPermissionGranted) {
                permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
            }
            if (permissionsToRequest.isNotEmpty()) {
                permissionLauncher.launch(permissionsToRequest.toTypedArray())
            }
        }


        LaunchedEffect(Unit) {
            requestPermissions()
        }

        if (!vitalsPermissionGranted) {
            PermissionsScreen(onGrantPermission = { requestPermissions() })
        } else {
            // Main app content
            LaunchedEffect(hr) {
                heartRateViewModel.updateHeartRate(hr)
                heartRateAlertViewModel.checkHeartRate(hr.toFloat())
                highImpactViewModel.updateHeartRate(hr)
            }

            val breakInterval by breakReminderViewModel.breakInterval.collectAsState()

            LaunchedEffect(breakInterval, notificationsPermissionGranted) {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, BreakReminderReceiver::class.java).apply {
                    putExtra(BreakReminderReceiver.EXTRA_VIBRATION_PATTERN, VibrationPattern.LOW)
                }

                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    0, // request code
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                if (breakInterval > 0 && notificationsPermissionGranted) {
                    val intervalMillis = TimeUnit.MINUTES.toMillis(breakInterval.toLong())
                    alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis() + intervalMillis,
                        intervalMillis,
                        pendingIntent
                    )
                } else {
                    alarmManager.cancel(pendingIntent)
                }
            }

            LaunchedEffect(heartRateAlertViewModel.vibrationPattern.value) {
                if (vibrator.hasVibrator()) {
                    val vibrationPattern = when (heartRateAlertViewModel.vibrationPattern.value) {
                        VibrationPattern.LOW -> longArrayOf(0, 100, 200, 100, 200)
                        VibrationPattern.HIGH -> longArrayOf(0, 500, 200, 500, 200)
                        else -> null
                    }

                    if (vibrationPattern != null) {
                        val vibrationEffect = VibrationEffect.createWaveform(vibrationPattern, -1) // No repeat
                        vibrator.vibrate(vibrationEffect)
                    } else {
                        vibrator.cancel()
                    }
                }
            }

            DisposableEffect(Unit) {
                val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
                val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
                var lastReadingTime = System.currentTimeMillis()
                var lastMagnitude = 0.0
                val readings: MutableList<Pair<Double, Long>> = newMutableList()
                var lastVibrationAlert = 0L

                val sensorListener = object : SensorEventListener {
                    override fun onAccuracyChanged(
                        sensor: Sensor?,
                        accuracy: Int
                    ) {
                        Log.d("$sensor", "Accuracy Changed to $accuracy")
                    }

                    override fun onSensorChanged(event: SensorEvent?) {
                        if (viewModel.vibrationEnabled.value &&
                            event?.sensor?.type == Sensor.TYPE_LINEAR_ACCELERATION &&
                            !(heartRateAlertViewModel.alertState.value == AlertState.LOW ||
                                    heartRateAlertViewModel.alertState.value == AlertState.HIGH)) {
                            val magnitude = sqrt(
                                event.values[0].toDouble().pow(2.0) +
                                        event.values[1].toDouble().pow(2.0) +
                                        event.values[2].toDouble().pow(2.0)
                            )
                            val readingTime = System.currentTimeMillis()

                            if (magnitude > 0.1) {
                                readings.add(Pair(lastMagnitude, readingTime - lastReadingTime))

                                lastMagnitude = magnitude
                                lastReadingTime = readingTime

                                Log.d("Vibration", "Magnitude: $magnitude")

                                viewModel.vibration.value = calculateVibrationExposure(readings)
                                if (viewModel.vibration.value >= 2.5 && System.currentTimeMillis() - lastVibrationAlert >= 300000) {
                                    viewModel.vibrationAlert.value = true
                                    lastVibrationAlert = System.currentTimeMillis()
                                }
                            }
                        }
                    }
                }

                if (vitalsPermissionGranted) {
                    sensorManager.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
                }

                onDispose {
                    sensorManager.unregisterListener(sensorListener)
                }
            }

            AppScaffold(
                timeText = { TimeText() },
            ){
                SwipeDismissableNavHost(
                    navController = navController,
                    startDestination = "main"
                ) {
                    composable("main") {
                        MainScreen(navController, viewModel, highImpactViewModel)
                    }
                    composable("heartRate") {
                        HeartRateScreen(viewModel, navController)
                    }
                    composable("highImpactScreen") {
                        HighImpactScreen(viewModel = highImpactViewModel)
                    }
                    composable("setAlertZones") {
                        SettingsScreen(
                            minHeartRate = minHeartRate,
                            maxHeartRate = maxHeartRate,
                            onSetZone = { min, max ->
                                minHeartRate = min
                                maxHeartRate = max
                                navController.popBackStack()
                            }
                        )
                    }
                    composable("setHighImpactThreshold") {
                        HighImpactSettings(highImpactViewModel, navController)
                    }
                    composable("setBreakReminder") {
                        val showConfirmation by breakReminderViewModel.showConfirmation.collectAsState()
                        BreakReminderScreen(
                            breakInterval = breakInterval,
                            showConfirmation = showConfirmation,
                            onSetBreakInterval = { interval ->
                                if (notificationsPermissionGranted) {
                                    breakReminderViewModel.setBreakInterval(interval)
                                }
                            },
                            onHideConfirmation = {
                                breakReminderViewModel.hideConfirmation()
                                navController.popBackStack()
                            }
                        )
                    }
                    composable("graph") {
                        GraphScreen(
                            heartRateViewModel,
                            onNavigateBack = { navController.popBackStack()
                            }
                        )
                    }
                    composable("settingsScreen") {
                        SettingsMainScreen(
                            navController = navController,
                        )
                    }
                    composable("metricEnableScreen") {
                        MetricSettingsScreen(
                            viewModel = viewModel,
                        )
                    }
                    composable("distanceScreen") {
                        DistanceScreen(viewModel = viewModel)
                    }
                    composable("vibrationScreen") {
                        VibrationScreen(
                            viewModel = viewModel
                        )
                    }
                }
                if (heartRateAlertViewModel.alertState.value != AlertState.NONE) {
                    GlobalAlert(hr.roundToInt(), heartRateAlertViewModel)
                }
                if (vibration >= 2.5 && viewModel.vibrationAlert.value) {
                    VibrationAlert(vibration, viewModel)
                }
            }
        }
    }
}

@Composable
fun GlobalAlert(hr: Int, viewModel: HeartRateAlertViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black, shape = CircleShape),
        contentAlignment = Alignment.Center,

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val alertMessage = when (viewModel.alertState.value) {
                AlertState.LOW -> "Heart rate is too low!"
                AlertState.HIGH -> "Heart rate is too high!"
                else -> ""
            }
            Text(
                text = alertMessage,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Current: $hr BPM",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { viewModel.clearAlert() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear Alert"
                )
            }
        }
    }
}
@Composable
fun VibrationAlert(vibration: Double, viewModel: DataViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            if (vibration >= 2.5 && vibration < 5.0) {
                Text(
                    text = "Average vibration over 2.5m/s²",
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Action Recommended!",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = { viewModel.vibrationAlert.value = false }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dismiss Alert"
                    )
                }
            } else if(vibration >= 5.0) {
                Text(
                    text = "Average vibration\nover 5.0m/s²",
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "More exposure may cause permanent injury!",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = { viewModel.vibrationAlert.value = false }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dismiss Alert"
                    )
                }
            }
        }
    }

}