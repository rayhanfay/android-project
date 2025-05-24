package com.rayhan.mytask.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rayhan.mytask.TaskMasterApplication
import com.rayhan.mytask.data.Task
import com.rayhan.mytask.data.TaskPriority
import com.rayhan.mytask.databinding.FragmentAddTaskBinding
import com.rayhan.mytask.services.ReminderService
import com.rayhan.mytask.viewmodels.TaskViewModel
import com.rayhan.mytask.viewmodels.TaskViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskViewModel: TaskViewModel
    private var selectedDueDate: Date = Calendar.getInstance().time
    private val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val taskRepository = (requireActivity().application as TaskMasterApplication).repository
        val factory = TaskViewModelFactory(requireActivity().application, taskRepository)
        taskViewModel = ViewModelProvider(requireActivity(), factory)[TaskViewModel::class.java]

        setupPrioritySpinner()
        setupDatePicker()

        binding.btnSave.setOnClickListener {
            saveTask()
        }
    }

    private fun setupPrioritySpinner() {
        val priorities = TaskPriority.values().map { it.name }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPriority.adapter = adapter
    }

    private fun setupDatePicker() {
        // Set default date to today
        binding.etDueDate.setText(dateFormatter.format(selectedDueDate))

        binding.etDueDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            if (binding.etDueDate.text.toString().isNotEmpty()) {
                try {
                    calendar.time = dateFormatter.parse(binding.etDueDate.text.toString()) ?: Date()
                } catch (e: Exception) {
                    calendar.time = Date()
                }
            }

            DatePickerDialog(
                requireContext(),
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

    private fun saveTask() {
        val title = binding.etTitle.text.toString().trim()
        val description = binding.etDescription.text.toString().trim()
        val priority = TaskPriority.valueOf(binding.spinnerPriority.selectedItem.toString())

        if (title.isEmpty()) {
            Toast.makeText(requireContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        // Create new task
        val newTask = Task(
            title = title,
            description = description,
            dueDate = selectedDueDate,
            priority = priority
        )

        taskViewModel.insert(newTask)

        // Schedule reminder
        ReminderService.scheduleReminder(requireContext(), newTask)

        Toast.makeText(requireContext(), "Task created successfully", Toast.LENGTH_SHORT).show()

        // Clear form
        binding.etTitle.setText("")
        binding.etDescription.setText("")
        binding.spinnerPriority.setSelection(0)

        // Reset due date to today
        selectedDueDate = Calendar.getInstance().time
        binding.etDueDate.setText(dateFormatter.format(selectedDueDate))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}