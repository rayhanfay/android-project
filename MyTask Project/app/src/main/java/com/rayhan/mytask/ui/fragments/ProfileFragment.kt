package com.rayhan.mytask.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rayhan.mytask.TaskMasterApplication
import com.rayhan.mytask.data.UserRepository
import com.rayhan.mytask.databinding.FragmentProfileBinding
import com.rayhan.mytask.ui.login.LoginActivity
import com.rayhan.mytask.utils.PreferencesManager
import com.rayhan.mytask.viewmodels.TaskViewModel
import com.rayhan.mytask.viewmodels.TaskViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.appcompat.app.AppCompatDelegate

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var userRepository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferencesManager = PreferencesManager(requireContext())

        // Initialize repositories and ViewModel
        val app = requireActivity().application as TaskMasterApplication
        val taskRepository = app.repository
        userRepository = UserRepository(app.database.userDao())

        val factory = TaskViewModelFactory(requireActivity().application, taskRepository)
        taskViewModel = ViewModelProvider(requireActivity(), factory)[TaskViewModel::class.java]

        loadUserProfile()
        setupTaskStatistics()
        setupToggleSwitches()

        binding.btnLogout.setOnClickListener {
            logoutUser()
        }
    }

    private fun loadUserProfile() {
        val username = preferencesManager.currentUsername

        if (username != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val user = userRepository.getUserByUsername(username)

                withContext(Dispatchers.Main) {
                    if (user != null) {
                        binding.tvUsername.text = user.username
                        binding.tvName.text = user.name
                        binding.tvEmail.text = user.email
                    }
                }
            }
        }
    }

    private fun setupTaskStatistics() {
        taskViewModel.activeTasks.observe(viewLifecycleOwner) { tasks ->
            binding.tvActiveTasks.text = tasks.size.toString()
        }

        taskViewModel.completedTasks.observe(viewLifecycleOwner) { tasks ->
            binding.tvCompletedTasks.text = tasks.size.toString()
        }
//
//        taskViewModel.getUpcomingTasks().observe(viewLifecycleOwner) { tasks ->
//            binding.tvUpcomingTasks.text = tasks.size.toString()
//        }
    }

    private fun setupToggleSwitches() {
        // Dark mode toggle
        binding.switchDarkMode.isChecked = preferencesManager.isDarkMode
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            preferencesManager.isDarkMode = isChecked
            // Apply theme change immediately
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
            // Recreate the activity to apply theme changes
            requireActivity().recreate()
        }

        // Notifications toggle
        binding.switchNotifications.isChecked = preferencesManager.isNotificationEnabled
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            preferencesManager.isNotificationEnabled = isChecked
        }
    }

    private fun logoutUser() {
        preferencesManager.clearUserData()

        val intent = Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}