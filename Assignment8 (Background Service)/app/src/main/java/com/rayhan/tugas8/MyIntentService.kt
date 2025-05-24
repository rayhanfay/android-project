package com.rayhan.tugas8

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class MyIntentService : JobIntentService() {
    init {
        instance = this
    }

    companion object {
        private lateinit var instance: MyIntentService
        private var isRunning = false

        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, MyIntentService::class.java, jobId, intent)
            Log.d(tag, "Servicenya mulai.............")
        }

        fun stopService() {
            Log.d(tag, "Service berhenti")
            isRunning = false
            instance.stopSelf()
        }

        private const val jobId = 1
        private const val tag = "MyIntentService"
    }

    override fun onHandleWork(intent: Intent) {
        try {
            isRunning = true
            while (isRunning) {
                Log.d(tag, "Service sedang berjalan")
                Thread.sleep(1000)
            }
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }
}