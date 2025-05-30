package com.rayhan.mytask.data

import androidx.room.TypeConverter
import com.rayhan.mytask.data.TaskPriority
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromPriority(priority: TaskPriority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(value: String): TaskPriority {
        return enumValueOf(value)
    }
}