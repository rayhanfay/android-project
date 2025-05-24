package com.rayhan.tugas77

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.format.DateFormat
import androidx.core.app.NotificationCompat
import timber.log.Timber
import java.util.concurrent.TimeUnit
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val timeInMillis = intent.getLongExtra(Constants.EXTRA_EXACT_ALARM_TIME, 0L)
        when (intent.action) {
            Constants.ACTION_SET_EXACT -> {
                buatNotifikasi(context, "Ini Alarm saat ini", konversiTanggal(timeInMillis))
            }
            Constants.ACTION_SET_REPETITIVE_EXACT -> {
                setAlarmBerulang(AlarmService(context))
                buatNotifikasi(context, "Ini Alarm berulang", konversiTanggal(timeInMillis))
            }
        }
    }

    private var notificationId = 0

    private fun buatNotifikasi(context: Context, title: String, message: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // For Android 8.0 and above (Oreo+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "alarm_channel",
                "Alarm Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, "alarm_channel")
            .setContentTitle(title)
            .setContentText("Alarm nyala pada pukul: - $message")
            .setSmallIcon(R.drawable.ic_alarm) // Make sure you have this icon
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    private fun setAlarmBerulang(alarmService: AlarmService) {
        val cal = Calendar.getInstance().apply {
            this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(7)
            Timber.d("Pasang alarm untuk minggu pada waktu yang sama" +
                    " ${konversiTanggal(this.timeInMillis)}")
        }
        alarmService.setAlarmBerulang(cal.timeInMillis)
    }

    private fun konversiTanggal(timeInMillis: Long): String =
        DateFormat.format("dd/MM/yyyy hh:mm:ss", timeInMillis).toString()
}