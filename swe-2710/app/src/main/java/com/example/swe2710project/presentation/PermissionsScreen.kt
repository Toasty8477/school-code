package com.example.swe2710project.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun PermissionsScreen(
    onGrantPermission: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as Activity

    var permanentlyDenied by remember { mutableStateOf(false) }

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        state = rememberScalingLazyListState(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Permissions Required",
                style = MaterialTheme.typography.title3,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.85f)
            )
        }

        item { Spacer(modifier = Modifier.height(6.dp)) }

        item {
            Text(
                text = "Enable notifications and sensors",
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.85f)
            )
        }

        item { Spacer(modifier = Modifier.height(12.dp)) }

        item {
            Button(
                modifier = Modifier
                    .width(140.dp)
                    .height(48.dp),
                onClick = {
                    if (permanentlyDenied) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivity(intent)
                    } else {
                        val showNotificationRationale =
                            ActivityCompat.shouldShowRequestPermissionRationale(
                                activity,
                                Manifest.permission.POST_NOTIFICATIONS
                            )

                        val showSensorRationale =
                            ActivityCompat.shouldShowRequestPermissionRationale(
                                activity,
                                Manifest.permission.BODY_SENSORS
                            )

                        if (!showNotificationRationale || !showSensorRationale) {
                            permanentlyDenied = true
                        } else {
                            onGrantPermission()
                        }
                    }
                }
            ) {
                Text(text = if (permanentlyDenied) "Open Settings" else "Grant")
            }
        }

        if (permanentlyDenied) {
            item { Spacer(modifier = Modifier.height(6.dp)) }

            item {
                Text(
                    text = "Enable in system settings",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(0.85f)
                )
            }
        }
    }
}