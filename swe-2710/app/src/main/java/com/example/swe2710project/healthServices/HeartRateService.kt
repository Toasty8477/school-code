package com.example.swe2710project.healthServices

import android.app.PendingIntent
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.health.services.client.MeasureCallback
import androidx.health.services.client.data.Availability
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.DeltaDataType
import androidx.health.services.client.unregisterMeasureCallback
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.swe2710project.data.DataRepository
import com.example.swe2710project.presentation.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * A foreground service that tracks heart rate data using Health Services.
 * It remains active even when the app is in the background.
 */
class HeartRateService : LifecycleService() {

    private lateinit var activeDataCollection: ActiveDataCollection
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var isTracking = false
    private var measureJob: Job? = null

    // The callback that receives heart rate data.
    private val heartRateCallback = object : MeasureCallback {
        override fun onAvailabilityChanged(dataType: DeltaDataType<*, *>, availability: Availability) { // <--- CORRECTED
            // No implementation needed for this use case. Can be used to update UI
            // about sensor availability.
        }

        override fun onDataReceived(data: DataPointContainer) {
            data.getData(DataType.HEART_RATE_BPM).lastOrNull()?.let {
                Log.d(TAG, "Service received HR: ${it.value}")
                DataRepository.updateHr(it.value)
            }
        }
    }


    override fun onCreate() {
        super.onCreate()
        activeDataCollection = ActiveDataCollection(this)
        // This is a good place to create the channel once.
        activeDataCollection.createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        when (intent?.action) {
            ACTION_START -> startTracking()
            ACTION_STOP -> stopTracking()
        }
        return START_STICKY
    }

    private fun startTracking() {
        if (isTracking) return
        isTracking = true
        Log.d(TAG, "Starting heart rate tracking.")

        measureJob = lifecycleScope.launch {
            try {
                // 1. Create intent and builders
                val pendingIntent = createActivityPendingIntent()
                val notificationBuilder = activeDataCollection.createNotificationBuilder(pendingIntent)
                val ongoingActivity = activeDataCollection.buildOngoingActivity(notificationBuilder, pendingIntent)

                ongoingActivity.apply(applicationContext)

                // 2. Use the service's OWN constants for startForeground
                startForeground(NOTIFICATION_ID, notificationBuilder.build())

                // 3. Register callback
                activeDataCollection.measureClient.registerMeasureCallback(DataType.HEART_RATE_BPM, heartRateCallback)

            } catch (e: Exception) {
                Log.e(TAG, "Error starting measurement", e)
                stopTracking()
            }
        }
    }

    private fun stopTracking() {
        if (!isTracking) return // Not tracking
        isTracking = false
        Log.d(TAG, "Stopping heart rate tracking.")

        // Cancel the measurement coroutine.
        measureJob?.cancel()

        // Use the service's scope to unregister the callback.
        serviceScope.launch {
            try {
                activeDataCollection.measureClient.unregisterMeasureCallback(DataType.HEART_RATE_BPM, heartRateCallback)
            } catch (e: Exception) {
                Log.e(TAG, "Error unregistering callback", e)
            } finally {
                // Stop the foreground service and remove the notification.
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf() // Stop the service itself.
            }
        }
    }

    private fun createActivityPendingIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        return PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        // Ensure all coroutines are cancelled when the service is destroyed.
        serviceScope.cancel()
        Log.d(TAG, "HeartRateService destroyed.")
    }

    // A binder is not needed for this service type, so we return null.
    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    // THIS IS THE CRUCIAL PART THAT MUST BE CORRECT
    companion object {
        private const val TAG = "HeartRateService"
        const val NOTIFICATION_ID = 101

        // These constants must exist for ActiveDataCollection to compile
        const val NOTIFICATION_CHANNEL_ID = "hr_tracking_channel"
        const val NOTIFICATION_CHANNEL_NAME = "Heart Rate Tracking"

        const val ACTION_START = "com.example.swe2710project.ACTION_START"
        const val ACTION_STOP = "com.example.swe2710project.ACTION_STOP"
    }

}


