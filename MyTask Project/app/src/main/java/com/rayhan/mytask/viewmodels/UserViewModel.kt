package com.rayhan.mytask.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rayhan.mytask.data.User
import com.rayhan.mytask.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Idle)
    val registrationState: StateFlow<RegistrationState> = _registrationState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            try {
                val user = repository.login(username, password)
                if (user != null) {
                    _loginState.value = LoginState.Success(user)
                } else {
                    _loginState.value = LoginState.Error("Invalid username or password")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Login failed: ${e.message}")
            }
        }
    }

    fun register(username: String, password: String, name: String, email: String) {
        viewModelScope.launch {
            _registrationState.value = RegistrationState.Loading

            try {
                // Check if username already exists
                val existingUser = repository.getUserByUsername(username)
                if (existingUser != null) {
                    _registrationState.value = RegistrationState.Error("Username already exists")
                    return@launch
                }

                // Check if email already exists
                val existingEmail = repository.getUserByEmail(email)
                if (existingEmail != null) {
                    _registrationState.value = RegistrationState.Error("Email already exists")
                    return@launch
                }

                // Create new user
                val newUser = User(
                    username = username,
                    password = password, // In a real app, this would be hashed
                    name = name,
                    email = email
                )

                repository.register(newUser)
                _registrationState.value = RegistrationState.Success
            } catch (e: Exception) {
                _registrationState.value =
                    RegistrationState.Error("Registration failed: ${e.message}")
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = LoginState.Idle
    }

    fun resetRegistrationState() {
        _registrationState.value = RegistrationState.Idle
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val user: User) : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class RegistrationState {
    object Idle : RegistrationState()
    object Loading : RegistrationState()
    object Success : RegistrationState()
    data class Error(val message: String) : RegistrationState()
}

class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}