package com.towhidcse.swpc.data.repository

import com.towhidcse.swpc.data.model.LoginRequest
import com.towhidcse.swpc.data.model.LoginResponse
import com.towhidcse.swpc.data.model.MobileLogin
import com.towhidcse.swpc.data.network.AuthApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class AuthRepository(private val apiService: AuthApiService) {
    fun login(login: LoginRequest): Flow<Result<LoginResponse>> = flow {
        try {
            val response = apiService.login(login)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.success(it))
                } ?: emit(Result.failure(Exception("Login failed: Empty response body")))
            } else {
                val errorMessage = response.errorBody()?.string() ?: response.message()
                emit(Result.failure(Exception("Login failed: ${errorMessage}")))
            }
        } catch (e: IOException) {
            emit(Result.failure(Exception("Network error: ${e.message}")))
        } catch (e: HttpException) {
            emit(Result.failure(Exception("Server error: ${e.message()}")))
        } catch (e: Exception) {
            emit(Result.failure(Exception("Unexpected error: ${e.message}")))
        }
    }

    fun mobileLogin(login: MobileLogin): Flow<Result<LoginResponse>> = flow {
        try {
            val response = apiService.mobileLogin(login)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.success(it))
                } ?: emit(Result.failure(Exception("Mobile login failed: Empty response body")))
            } else {
                val errorMessage = response.errorBody()?.string() ?: response.message()
                emit(Result.failure(Exception("Mobile login failed: $errorMessage")))
            }
        } catch (e: IOException) {
            emit(Result.failure(Exception("Network error: ${e.message}")))
        } catch (e: HttpException) {
            emit(Result.failure(Exception("Server error: ${e.message()}")))
        } catch (e: Exception) {
            emit(Result.failure(Exception("Unexpected error: ${e.message}")))
        }
    }
}