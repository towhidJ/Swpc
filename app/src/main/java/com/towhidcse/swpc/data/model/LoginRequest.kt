package com.towhidcse.swpc.data.model

data class LoginRequest(
    val email: String,
    val password: String
)


data class MobileLogin(
    val mobile: String
)