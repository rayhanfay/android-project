package com.rayhan.mytask

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.Configuration
import androidx.work.*
import java.util.concurrent.TimeUnit
import com.rayhan.mytask.data.AppDatabase
import com.rayhan.mytask.data.TaskRepository
import com.rayhan.mytask.ui.main.MainActivity
import com.rayhan.mytask.utils.PreferencesManager
import com.rayhan.mytask.workers.TaskCleanupWorker

class TaskMasterApplication : Application(), Configuration.Provider {

    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { TaskRepository(database.taskDao()) }
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate() {
        super.onCreate()

        // Initialize preferences manager early
        preferencesManager = PreferencesManager(this)

        // Apply saved dark mode setting
        applyDarkModePreference()

        createNotificationChannel()
        setupRecurringWork()
    }

    private fun applyDarkModePreference() {
        // Apply the dark mode preference from SharedPreferences
        val isDarkMode = preferencesManager.isDarkMode
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .build()

        // Task cleanup - runs weekly
        val cleanupRequest = PeriodicWorkRequestBuilder<TaskCleanupWorker>(7, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "task_cleanup_work",
            ExistingPeriodicWorkPolicy.KEEP,
            cleanupRequest
        )
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "task_reminder_channel"
            val channelName = "Task Reminders"
            val channelDescription = "Notifications for task reminders"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
    }
}