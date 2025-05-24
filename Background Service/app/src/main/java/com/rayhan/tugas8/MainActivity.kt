package com.rayhan.tugas8

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rayhan.tugas8.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            MyIntentService.enqueueWork(context = this, Intent())
            binding.txtHasil.text = "Service Jalan"
        }

        binding.btnStop.setOnClickListener {
            MyIntentService.stopService()
            binding.txtHasil.text = "Service Berhenti"
        }
    }
}