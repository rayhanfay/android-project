package com.rayhan.mytask.ui.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rayhan.mytask.R
import com.rayhan.mytask.data.Task
import com.rayhan.mytask.data.TaskPriority
import com.rayhan.mytask.databinding.ItemTaskBinding
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(
    private val onItemClick: (Task) -> Unit,
    private val onCheckboxClick: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }

            binding.checkbox.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = getItem(position)
                    // Temporarily disable to prevent multiple clicks
                    binding.checkbox.isEnabled = false
                    onCheckboxClick(task)
                    // Re-enable after a short delay
                    binding.checkbox.postDelayed({
                        binding.checkbox.isEnabled = true
                    }, 300) // 300ms delay
                }
            }
        }

        fun bind(task: Task) {
            binding.tvTitle.text = task.title
            binding.tvDescription.text = task.description
            binding.tvDueDate.text = dateFormatter.format(task.dueDate)
            binding.checkbox.isChecked = task.isCompleted

            // Set priority color
            val priorityColor = when (task.priority) {
                TaskPriority.HIGH -> ContextCompat.getColor(binding.root.context, R.color.colorSecondary)
                TaskPriority.MEDIUM -> ContextCompat.getColor(binding.root.context, R.color.colorAccent)
                TaskPriority.LOW -> ContextCompat.getColor(binding.root.context, R.color.colorTextSecondary)
            }
            binding.priorityIndicator.setBackgroundColor(priorityColor)

            // Apply strikethrough if task is completed
            if (task.isCompleted) {
                binding.tvTitle.paintFlags = binding.tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.tvTitle.paintFlags = binding.tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            // Pastikan memeriksa semua properti yang relevan, terutama isCompleted
            return oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.description == newItem.description &&
                    oldItem.isCompleted == newItem.isCompleted &&
                    oldItem.priority == newItem.priority &&
                    oldItem.dueDate.time == newItem.dueDate.time
        }
    }
}
