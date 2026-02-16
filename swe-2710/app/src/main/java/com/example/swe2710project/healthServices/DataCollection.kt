/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.healthServices

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import androidx.health.services.client.HealthServices
import androidx.health.services.client.data.DataType
import androidx.wear.ongoing.OngoingActivity
import androidx.wear.ongoing.Status
import com.example.swe2710project.R
import kotlinx.coroutines.guava.await

class ActiveDataCollection(private val context: Context) {
    val healthClient = HealthServices.getClient(context)
    val measureClient = healthClient.measureClient

    val tag = "MeasureClient: "

    fun createNotificationChannel() {
        // Use constants from the service that now "owns" the notification.
        val channel = NotificationChannel(
            HeartRateService.NOTIFICATION_CHANNEL_ID,
            HeartRateService.NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    fun createNotificationBuilder(pendingIntent: PendingIntent): NotificationCompat.Builder {
        // Create the NotificationCompat.Builder using the intent that was passed in.
        return NotificationCompat.Builder(context, HeartRateService.NOTIFICATION_CHANNEL_ID)
            .setContentTitle(HeartRateService.NOTIFICATION_CHANNEL_NAME)
            .setContentText("Tracking your heart rate...")
            .setSmallIcon(R.drawable.ic_favorite)
            .setCategory(NotificationCompat.CATEGORY_WORKOUT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
    }

    fun buildOngoingActivity(
        notificationBuilder: NotificationCompat.Builder,
        pendingIntent: PendingIntent
    ): OngoingActivity {
        val statusTemplate = "Tracking HR: #time#"
        val ongoingActivityStatus = Status.Builder()
            .addTemplate(statusTemplate)
            .addPart("time", Status.StopwatchPart(SystemClock.elapsedRealtime()))
            .build()

        return OngoingActivity.Builder(context, HeartRateService.NOTIFICATION_ID, notificationBuilder)
            .setAnimatedIcon(R.drawable.ic_favorite)
            .setStaticIcon(R.drawable.ic_favorite)
            .setTouchIntent(pendingIntent)
            .setStatus(ongoingActivityStatus)
            .build()
    }

    suspend fun hasHeartRateCapability(): Boolean {
        val capabilities = measureClient.getCapabilitiesAsync().await()
        return (DataType.HEART_RATE_BPM in capabilities.supportedDataTypesMeasure)
    }
}