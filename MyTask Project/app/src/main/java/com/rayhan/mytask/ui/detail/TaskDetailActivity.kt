package com.rayhan.mytask.ui.detail

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rayhan.mytask.R
import com.rayhan.mytask.TaskMasterApplication
import com.rayhan.mytask.data.Task
import com.rayhan.mytask.data.TaskPriority
import com.rayhan.mytask.databinding.ActivityTaskDetailBinding
import com.rayhan.mytask.services.ReminderService
import com.rayhan.mytask.viewmodels.TaskViewModel
import com.rayhan.mytask.viewmodels.TaskViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskDetailActivity : AppCompatActivity() {
    private var isDeleting = false
    private lateinit var binding: ActivityTaskDetailBinding
    private lateinit var taskViewModel: TaskViewModel
    private var taskId: Int = -1
    private var task: Task? = null
    private var selectedDueDate: Date = Date()
    private val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize ViewModel
        val taskRepository = (application as TaskMasterApplication).repository
        val factory = TaskViewModelFactory(application, taskRepository)
        taskViewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]

        // Get task ID from intent
        taskId = intent.getIntExtra("TASK_ID", -1)

        setupPrioritySpinner()
        setupDatePicker()

        if (taskId != -1) {
            loadTask()
        } else {
            title = "Create New Task"
        }

        binding.btnSave.setOnClickListener {
            saveTask()
        }
    }

    private fun setupPrioritySpinner() {
        val priorities = TaskPriority.values().map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPriority.adapter = adapter
    }

    private fun setupDatePicker() {
        binding.etDueDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            if (binding.etDueDate.text?.toString()?.isNotEmpty() == true) {
                try {
                    calendar.time = dateFormatter.parse(binding.etDueDate.text.toString()) ?: Date()
                } catch (e: Exception) {
                    calendar.time = Date()
                }
            }

            DatePickerDialog(
                this,
                { _, year, month, day ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, day)
                    selectedDueDate = calendar.time
                    binding.etDueDate.setText(dateFormatter.format(selectedDueDate))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun loadTask() {
        if (isDeleting) return

        taskViewModel.getTask(taskId).observe(this) { loadedTask ->
            if (isDeleting) return@observe

            task = loadedTask
            title = "Edit Task"

            binding.etTitle.setText(loadedTask.title)
            binding.etDescription.setText(loadedTask.description)
            binding.etDueDate.setText(dateFormatter.format(loadedTask.dueDate))
            selectedDueDate = loadedTask.dueDate

            val priorityPosition = TaskPriority.values().indexOf(loadedTask.priority)
            binding.spinnerPriority.setSelection(priorityPosition)

            binding.checkboxCompleted.isChecked = loadedTask.isCompleted
        }
    }

    private fun saveTask() {
        val title = binding.etTitle.text.toString().trim()
        val description = binding.etDescription.text.toString().trim()
        val priority = TaskPriority.valueOf(binding.spinnerPriority.selectedItem.toString())
        val isCompleted = binding.checkboxCompleted.isChecked

        if (title.isEmpty()) {
            Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        if (task != null) {
            // Update existing task
            val updatedTask = task!!.copy(
                title = title,
                description = description,
                dueDate = selectedDueDate,
                priority = priority,
                isCompleted = isCompleted,
                modifiedAt = Date()
            )

            taskViewModel.update(updatedTask)

            // Update reminder
            if (!isCompleted) {
                ReminderService.scheduleReminder(this, updatedTask)
            } else {
                ReminderService.cancelReminder(this, updatedTask.id)
            }
        } else {
            // Create new task
            val newTask = Task(
                title = title,
                description = description,
                dueDate = selectedDueDate,
                priority = priority,
                isCompleted = isCompleted
            )

            // Insert and get the ID
            taskViewModel.insert(newTask)

            // Schedule reminder
            if (!isCompleted) {
                ReminderService.scheduleReminder(this, newTask)
            }
        }

        Toast.makeText(this, "Task saved successfully", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (taskId != -1) {
            menuInflater.inflate(R.menu.menu_task_detail, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            R.id.action_delete -> {
                showDeleteConfirmationDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun stopObservingTask() {
        if (taskId != -1) {
            taskViewModel.getTask(taskId).removeObservers(this)
        }
    }

    override fun onDestroy() {
        stopObservingTask()
        super.onDestroy()
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete Task")
            .setMessage("Are you sure you want to delete this task?")
            .setPositiveButton("Delete") { _, _ ->
                task?.let { taskToDelete ->
                    // First stop observing the task
                    stopObservingTask()
                    isDeleting = true  // Add this flag to prevent race conditions

                    // Cancel reminder
                    ReminderService.cancelReminder(this, taskToDelete.id)

                    // Delete task BEFORE finishing the activity
                    taskViewModel.delete(taskToDelete)

                    // Set result and finish
                    Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}