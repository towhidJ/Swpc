package com.towhidcse.swpc.data.network


import com.towhidcse.swpc.data.model.LoginRequest
import com.towhidcse.swpc.data.model.LoginResponse
import com.towhidcse.swpc.data.model.MobileLogin
import com.towhidcse.swpc.data.model.Post
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/login")
    suspend fun login(@Body login: LoginRequest): Response<LoginResponse>

    @POST("mobileLogin")
    suspend fun mobileLogin(@Body login: MobileLogin): Response<LoginResponse>
}