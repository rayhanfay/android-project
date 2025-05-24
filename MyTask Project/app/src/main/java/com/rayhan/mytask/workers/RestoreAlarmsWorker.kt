package com.rayhan.mytask.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rayhan.mytask.TaskMasterApplication
import com.rayhan.mytask.services.ReminderService
import kotlinx.coroutines.flow.first

class RestoreAlarmsWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val repository = (applicationContext as TaskMasterApplication).repository
        val tasks = repository.activeTasks.first()

        // Restore reminders for all active tasks
        for (task in tasks) {
            if (!task.isCompleted) {
                ReminderService.scheduleReminder(applicationContext, task)
            }
        }

        return Result.success()
    }
}