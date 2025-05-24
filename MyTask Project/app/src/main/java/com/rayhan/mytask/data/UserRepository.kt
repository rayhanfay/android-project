package com.rayhan.mytask.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import java.util.Date

class UserRepository(private val userDao: UserDao) {

    @WorkerThread
    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    @WorkerThread
    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    @WorkerThread
    suspend fun login(username: String, password: String): User? {
        return userDao.login(username, password)
    }

    @WorkerThread
    suspend fun register(user: User) {
        userDao.register(user)
    }

    @WorkerThread
    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
}