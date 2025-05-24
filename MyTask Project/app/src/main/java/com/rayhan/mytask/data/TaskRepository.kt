package com.rayhan.mytask.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import java.util.Date

class TaskRepository(private val taskDao: TaskDao) {

    // Room executes all queries on a separate thread
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()
    val activeTasks: Flow<List<Task>> = taskDao.getActiveTasks()
    val completedTasks: Flow<List<Task>> = taskDao.getCompletedTasks()

    fun getTask(id: Int): Flow<Task> {
        return taskDao.getTask(id)
    }

    fun getTasksDueBetween(startDate: Date, endDate: Date): Flow<List<Task>> {
        return taskDao.getTasksDueBetween(startDate, endDate)
    }

    @WorkerThread
    suspend fun insert(task: Task): Long {
        return taskDao.insert(task)
    }

    @WorkerThread
    suspend fun update(task: Task) {
        taskDao.update(task)
    }

    @WorkerThread
    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }

    suspend fun getCompletedTasksBefore(date: Date): List<Task> {
        return taskDao.getCompletedTasksBefore(date)
    }
}