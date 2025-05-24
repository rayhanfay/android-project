package com.rayhan.mytask.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.rayhan.mytask.data.Task
import com.rayhan.mytask.data.TaskRepository
import com.rayhan.mytask.services.ReminderService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class TaskViewModel(
    application: Application,
    private val repository: TaskRepository
) : AndroidViewModel(application) {

    val allTasks: LiveData<List<Task>> = repository.allTasks.asLiveData()
    val activeTasks: LiveData<List<Task>> = repository.activeTasks.asLiveData()
    val completedTasks: LiveData<List<Task>> = repository.completedTasks.asLiveData()

    fun getTask(id: Int): LiveData<Task> {
        return repository.getTask(id).asLiveData()
    }

    fun getUpcomingTasks(): LiveData<List<Task>> {
        val currentDate = Date()
        val oneWeekLater = Calendar.getInstance().apply {
            time = currentDate
            add(Calendar.DAY_OF_YEAR, 7)
        }.time

        return repository.getTasksDueBetween(currentDate, oneWeekLater).asLiveData()
    }

    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        repository.update(task)
    }

    fun delete(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.delete(task)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun toggleTaskCompletion(task: Task) = viewModelScope.launch {
        val updatedTask = task.copy(
            isCompleted = !task.isCompleted,
            modifiedAt = Date()
        )
        repository.update(updatedTask)

        // Now getApplication() will work correctly
        if (updatedTask.isCompleted) {
            ReminderService.cancelReminder(getApplication(), updatedTask.id)
        } else {
            ReminderService.scheduleReminder(getApplication(), updatedTask)
        }
    }
}

class TaskViewModelFactory(
    private val application: Application,
    private val repository: TaskRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}