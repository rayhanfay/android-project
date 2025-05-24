package com.rayhan.mytask.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.rayhan.mytask.workers.RestoreAlarmsWorker
import java.util.concurrent.TimeUnit

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Schedule work to restore all alarms
            val workRequest = OneTimeWorkRequestBuilder<RestoreAlarmsWorker>()
                .setInitialDelay(5, TimeUnit.SECONDS) // Small delay to ensure system is ready
                .build()

            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}