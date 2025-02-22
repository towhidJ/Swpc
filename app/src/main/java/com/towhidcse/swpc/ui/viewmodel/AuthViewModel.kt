package com.towhidcse.swpc.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towhidcse.swpc.data.model.LoginRequest
import com.towhidcse.swpc.data.model.LoginResponse
import com.towhidcse.swpc.data.model.MobileLogin
import com.towhidcse.swpc.data.network.RetrofitClient
import com.towhidcse.swpc.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {
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

    // Function to handle Email login
    fun mobileLogin(login: LoginRequest) {
        viewModelScope.launch {
            // Show loading indicator
            _isLoading.value = true
            try {
                // Attempt login using the repository
                repository.login(login).collect { result ->
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