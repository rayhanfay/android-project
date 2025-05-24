package com.rayhan.mytask.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.rayhan.mytask.TaskMasterApplication
import com.rayhan.mytask.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*

class SyncService : LifecycleService() {

    private lateinit var repository: com.rayhan.mytask.data.TaskRepository

    override fun onCreate() {
        super.onCreate()
        repository = (application as TaskMasterApplication).repository
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        lifecycleScope.launch(Dispatchers.IO) {
            // This is where you'd normally sync with a remote database
            // For this example, we'll just check for overdue tasks and mark them
            checkForOverdueTasks()
        }

        return START_NOT_STICKY
    }

    private suspend fun checkForOverdueTasks() {
        val now = Date()
        repository.activeTasks.first().forEach { task ->
            if (task.dueDate.before(now) && !task.isCompleted) {
                // Mark task as overdue (could add an 'isOverdue' field in a real app)
                // Or modify its priority
                val updatedTask = task.copy(
                    priority = com.rayhan.mytask.data.TaskPriority.HIGH,
                    modifiedAt = now
                )
                repository.update(updatedTask)
            }
        }

        // Stop the service once finished
        stopSelf()
    }
}