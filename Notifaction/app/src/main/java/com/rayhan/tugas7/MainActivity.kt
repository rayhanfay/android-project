package com.rayhan.tugas7

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.rayhan.tugas7.databinding.ActivityMainBinding
import android.Manifest


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    val CHANNEL_ID = "channelId"
    val CHANNEL_NAME = "channelName"
    val NOTIFICATION_ID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buatNotifikasi()

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Belajar Notifikasi")
            .setContentText("Halo! notifikasi kito yo")
            .setSmallIcon(R.drawable.ic_notif)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)

        binding.btnKirimNotif.setOnClickListener {
            // For Android 10, you need to check VIBRATE permission if your notification uses vibration
            // For basic notifications without special features, this might not be necessary
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.VIBRATE
                ) == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                notificationManager.notify(NOTIFICATION_ID, notification)
            } else {
                // Handle the case where permission is not available
                Log.e("MainActivity", "Vibrate permission not granted")

                // You can still show the notification without vibration
                // by rebuilding it without vibration pattern
                val notificationWithoutVibration = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Belajar Notifikasi")
                    .setContentText("Halo! notifikasi kito yo")
                    .setSmallIcon(R.drawable.ic_notif)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    // Don't set vibration pattern
                    .build()

                notificationManager.notify(NOTIFICATION_ID, notificationWithoutVibration)
            }
        }
    }

    fun buatNotifikasi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel description"
                lightColor = Color.GREEN
                enableLights(true)
            }

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}