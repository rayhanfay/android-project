package com.rayhan.mytask.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.rayhan.mytask.TaskMasterApplication
import com.rayhan.mytask.data.UserRepository
import com.rayhan.mytask.databinding.ActivityLoginBinding
import com.rayhan.mytask.ui.main.MainActivity
import com.rayhan.mytask.utils.PreferencesManager
import com.rayhan.mytask.viewmodels.LoginState
import com.rayhan.mytask.viewmodels.UserViewModel
import com.rayhan.mytask.viewmodels.UserViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesManager = PreferencesManager(this)

        // Check if user is already logged in
        if (preferencesManager.isLoggedIn) {
            navigateToMainActivity()
            return
        }

        // Initialize ViewModel
        val userDao = (application as TaskMasterApplication).database.userDao()
        val repository = UserRepository(userDao)
        val factory = UserViewModelFactory(repository)
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            userViewModel.login(username, password)
        }

        binding.btnRegister.setOnClickListener {
            // Show registration layout and hide login layout
            binding.layoutLogin.visibility = View.GONE
            binding.layoutRegister.visibility = View.VISIBLE
        }

        binding.btnSubmitRegister.setOnClickListener {
            val username = binding.etRegisterUsername.text.toString().trim()
            val password = binding.etRegisterPassword.text.toString().trim()
            val name = binding.etRegisterName.text.toString().trim()
            val email = binding.etRegisterEmail.text.toString().trim()

            if (username.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate email format
            if (!isValidEmail(email)) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if username and name are identical
            if (username == name) {
                Toast.makeText(this, "Username and name cannot be the same", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate password format (must contain both letters and numbers)
            if (!isValidPassword(password)) {
                Toast.makeText(this, "Password must contain both letters and numbers", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            userViewModel.register(username, password, name, email)
        }

        binding.btnBackToLogin.setOnClickListener {
            // Show login layout and hide registration layout
            binding.layoutLogin.visibility = View.VISIBLE
            binding.layoutRegister.visibility = View.GONE
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val hasLetter = password.any { it.isLetter() }
        val hasDigit = password.any { it.isDigit() }
        return hasLetter && hasDigit
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            userViewModel.loginState.collect { state ->
                when (state) {
                    is LoginState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.btnLogin.isEnabled = false
                    }
                    is LoginState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnLogin.isEnabled = true

                        // Save user login state
                        preferencesManager.isLoggedIn = true
                        preferencesManager.currentUsername = state.user.username

                        // Navigate to main activity
                        navigateToMainActivity()
                    }
                    is LoginState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnLogin.isEnabled = true
                        Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnLogin.isEnabled = true
                    }
                }
            }
        }

        lifecycleScope.launch {
            userViewModel.registrationState.collect { state ->
                when (state) {
                    is com.rayhan.mytask.viewmodels.RegistrationState.Loading -> {
                        binding.registerProgressBar.visibility = View.VISIBLE
                        binding.btnSubmitRegister.isEnabled = false
                    }
                    is com.rayhan.mytask.viewmodels.RegistrationState.Success -> {
                        binding.registerProgressBar.visibility = View.GONE
                        binding.btnSubmitRegister.isEnabled = true
                        Toast.makeText(this@LoginActivity, "Registration successful! Please login.", Toast.LENGTH_SHORT).show()

                        // Show login layout and hide registration layout
                        binding.layoutLogin.visibility = View.VISIBLE
                        binding.layoutRegister.visibility = View.GONE
                    }
                    is com.rayhan.mytask.viewmodels.RegistrationState.Error -> {
                        binding.registerProgressBar.visibility = View.GONE
                        binding.btnSubmitRegister.isEnabled = true
                        Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        binding.registerProgressBar.visibility = View.GONE
                        binding.btnSubmitRegister.isEnabled = true
                    }
                }
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

