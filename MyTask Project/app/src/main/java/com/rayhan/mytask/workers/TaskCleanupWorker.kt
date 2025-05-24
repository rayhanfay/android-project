package com.rayhan.mytask.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rayhan.mytask.TaskMasterApplication
import com.rayhan.mytask.data.TaskPriority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class TaskCleanupWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val repository = (applicationContext as TaskMasterApplication).repository
            val now = Date()
            val calendar = Calendar.getInstance().apply {
                time = now
                add(Calendar.DAY_OF_MONTH, -30) // Get tasks older than 30 days
            }
            val cutoffDate = calendar.time

            // Get all completed tasks older than 30 days and archive them
            val oldTasks = repository.getCompletedTasksBefore(cutoffDate)

            // In a real app, you might move these to an archive collection
            // For this example, we'll just update their priority to LOW
            oldTasks.forEach { task ->
                val archivedTask = task.copy(
                    priority = TaskPriority.LOW,
                    modifiedAt = now
                )
                repository.update(archivedTask)
            }

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}