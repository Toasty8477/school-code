/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class BreakReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        showBreakNotification(context)

        val vibrationPattern = intent.getSerializableExtra(EXTRA_VIBRATION_PATTERN) as? VibrationPattern
        if (vibrationPattern != null) {
            (context.applicationContext as? MainApplication)?.triggerVibration(vibrationPattern)
        }
    }

    private fun showBreakNotification(context: Context) {
        val channelId = "break_reminder_channel"
        val channelName = "Break Reminder"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Time for a break!")
            .setContentText("You've been working for a while. Time to take a short break.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManager.notify(1, builder.build())
    }

    companion object {
        const val EXTRA_VIBRATION_PATTERN = "vibration_pattern"
    }
}
