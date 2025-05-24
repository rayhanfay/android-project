package com.rayhan.mytask.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "users")
data class User(
    @PrimaryKey val username: String,
    val password: String, // Note: In a real app, this should be securely hashed
    val name: String,
    val email: String
)