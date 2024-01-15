package io.aayush.relabs.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import io.aayush.relabs.MainActivity
import io.aayush.relabs.R
import io.aayush.relabs.network.XenforoRepository
import io.aayush.relabs.network.data.expo.ExpoData
import io.aayush.relabs.utils.CommonModule.ACCESS_TOKEN
import io.aayush.relabs.utils.CommonModule.FCM_TOKEN
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PushNotificationService : FirebaseMessagingService(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private val TAG = PushNotificationService::class.java.simpleName

    private lateinit var notificationManager: NotificationManager
    private val notificationChannel = "alerts"

    private val parentJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.IO + parentJob)

    @Inject
    lateinit var xenforoRepository: XenforoRepository

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannel,
                getString(R.string.alerts),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sharedPreferences.edit { putString(FCM_TOKEN, token) }
        registerPushNotifications()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (!key.isNullOrBlank() && key == ACCESS_TOKEN) registerPushNotifications()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.i(TAG, "Got FCM push notification!")

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, notificationChannel)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_notifications)
            .setColor(ContextCompat.getColor(this, R.color.ic_launcher_background))
            .setContentTitle(message.data["title"])
            .setContentText(message.data["message"])
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify((0..1000).random(), notification)
    }

    private fun registerPushNotifications() {
        val fcmToken = sharedPreferences.getString(FCM_TOKEN, "") ?: ""
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""
        if (accessToken.isNotBlank() && fcmToken.isNotBlank()) {
            serviceScope.launch {
                Log.i(TAG, "Registering FCM push notifications")
                xenforoRepository.registerPushNotifications(ExpoData(fcmToken))
            }
        }
    }
}
