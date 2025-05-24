package com.rayhan.mytask.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rayhan.mytask.R
import com.rayhan.mytask.TaskMasterApplication
import com.rayhan.mytask.databinding.ActivityMainBinding
import com.rayhan.mytask.ui.fragments.AddTaskFragment
import com.rayhan.mytask.ui.fragments.ProfileFragment
import com.rayhan.mytask.ui.fragments.TaskListFragment
import com.rayhan.mytask.utils.PreferencesManager
import com.rayhan.mytask.viewmodels.TaskViewModel
import com.rayhan.mytask.viewmodels.TaskViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var taskViewModel: TaskViewModel
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize preferences manager
        preferencesManager = PreferencesManager(this)

        // Ensure the theme is consistent with the preference
        // This is a safeguard in case the Application-level setting doesn't take effect
        AppCompatDelegate.setDefaultNightMode(
            if (preferencesManager.isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        val taskRepository = (application as TaskMasterApplication).repository
        val factory = TaskViewModelFactory(application, taskRepository)
        taskViewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]

        setupBottomNavigation()

        // Set default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, TaskListFragment())
                .commit()
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.navigation_tasks -> TaskListFragment()
                R.id.navigation_add_task -> AddTaskFragment()
                R.id.navigation_profile -> ProfileFragment()
                else -> TaskListFragment()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()

            true
        }
    }
}