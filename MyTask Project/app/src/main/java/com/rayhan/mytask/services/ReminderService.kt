package com.rayhan.mytask.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.rayhan.mytask.data.Task
import com.rayhan.mytask.receivers.NotificationReceiver
import java.util.*

object ReminderService {

    fun scheduleReminder(context: Context, task: Task) {
        try {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
            val intent = Intent(context, NotificationReceiver::class.java).apply {
                putExtra("TASK_ID", task.id)
                putExtra("TASK_TITLE", task.title)
            }

            // Use a unique request code for each task
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                task.id,  // Use task ID as request code
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Set alarm for the due date
            alarmManager?.set(
                AlarmManager.RTC_WAKEUP,
                task.dueDate.time,
                pendingIntent
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun cancelReminder(context: Context, taskId: Int) {
        try {
            // Safety check
            if (taskId <= 0) return

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
            val intent = Intent(context, NotificationReceiver::class.java)

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                taskId,  // Use task ID as request code
                intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            )

            // Only cancel if the pending intent exists
            if (pendingIntent != null) {
                alarmManager?.cancel(pendingIntent)
                pendingIntent.cancel()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}