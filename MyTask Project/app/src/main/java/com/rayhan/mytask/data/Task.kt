package com.rayhan.mytask.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

enum class TaskPriority {
    LOW, MEDIUM, HIGH
}

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val dueDate: Date,
    val priority: TaskPriority,
    val isCompleted: Boolean = false,
    val createdAt: Date = Date(),
    val modifiedAt: Date = Date()
)