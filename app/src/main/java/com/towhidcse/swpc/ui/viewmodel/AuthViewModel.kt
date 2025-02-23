package com.towhidcse.swpc.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towhidcse.swpc.data.model.LoginRequest
import com.towhidcse.swpc.data.model.LoginResponse
import com.towhidcse.swpc.data.model.MobileLogin
import com.towhidcse.swpc.data.network.RetrofitClient
import com.towhidcse.swpc.data.repository.AuthRepository
import com.towhidcse.swpc.util.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application): AndroidViewModel(application) {
    private val repository = AuthRepository(RetrofitClient.authApiService)

    // StateFlow to hold the login response
    private val _loginResponse = MutableStateFlow<LoginResponse?>(null)
    val loginResponse: StateFlow<LoginResponse?> = _loginResponse.asStateFlow()

    // StateFlow to hold any error message
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // StateFlow for loading state (optional)
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val context = application.applicationContext
    // Function to handle Email login
    fun emailLogin(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            // Show loading indicator
            _isLoading.value = true
            try {
                // Attempt login using the repository
                repository.login(LoginRequest(email, password)).collect { result ->
                    // Hide loading indicator
                    _isLoading.value = false

                    // Check if the result is successful or failed
                    if (result.isSuccess) {
                        // If login is successful, update the login response and clear any error messages
                        _loginResponse.value = result.getOrNull() // Get the successful response
                        val loginResponse = result.getOrNull()
                        loginResponse?.let {

                            val userPreferences = UserPreferences(context)  // context should be your application context
                            userPreferences.saveLoginData(it.accessToken, it.role)
                        }
                        onResult(true, "Login successful!")
                    } else {
                        // If login fails, clear the login response and set the error message
                        _loginResponse.value = null
                        val errorMessage = result.exceptionOrNull()?.message ?: "Unknown error"
                        _errorMessage.value = errorMessage
                        onResult(false, errorMessage)
                    }
                }
            } catch (e: Exception) {
                // If any unexpected error occurs, handle it here
                _isLoading.value = false
                _errorMessage.value = "An unexpected error occurred: ${e.message}"
                onResult(false, "An unexpected error occurred: ${e.message}")
            }
        }
    }

    // Function to handle mobile login
    fun mobileLogin(mobileLogin: MobileLogin) {
        viewModelScope.launch {
            // Show loading indicator
            _isLoading.value = true
            try {
                // Attempt login using the repository
                repository.mobileLogin(mobileLogin).collect { result ->
                    // Hide loading indicator
                    _isLoading.value = false

                    result.onSuccess { response ->
                        // If login is successful, update the login response and clear any error messages
                        _loginResponse.value = response
                        _errorMessage.value = null
                    }.onFailure { exception ->
                        // If login fails, clear the login response and set the error message
                        _loginResponse.value = null
                        _errorMessage.value = exception.message
                    }
                }
            } catch (e: Exception) {
                // If any unexpected error occurs, handle it here
                _isLoading.value = false
                _errorMessage.value = "An unexpected error occurred: ${e.message}"
            }
        }
    }
}