package com.rayhan.mytask.receivers

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.rayhan.mytask.R
import com.rayhan.mytask.ui.detail.TaskDetailActivity

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getIntExtra("TASK_ID", -1)
        val taskTitle = intent.getStringExtra("TASK_TITLE") ?: "Task reminder"
        val taskDescription = intent.getStringExtra("TASK_DESCRIPTION") ?: ""

        if (taskId != -1) {
            showTaskNotification(context, taskId, taskTitle, taskDescription)
        }
    }

    private fun showTaskNotification(context: Context, taskId: Int, taskTitle: String, taskDescription: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create intent for when notification is tapped
        val contentIntent = Intent(context, TaskDetailActivity::class.java).apply {
            putExtra("TASK_ID", taskId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            taskId,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Build notification - Use a default icon if the custom one is missing
        val notification = NotificationCompat.Builder(context, "task_reminder_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Using standard icon to avoid potential crashes
            .setContentTitle("Task Reminder")
            .setContentText("Don't forget: $taskTitle")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(taskId, notification)
    }
}