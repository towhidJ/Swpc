package com.towhidcse.swpc.util
import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // Keys
    private val ACCESS_TOKEN_KEY = "access_token"
    private val ROLE_KEY = "role"

    // Store the access token and role
    fun saveLoginData(accessToken: String, role: String) {
        sharedPreferences.edit().apply {
            putString(ACCESS_TOKEN_KEY, accessToken)
            putString(ROLE_KEY, role)
            apply()  // or commit() for synchronous operation
        }
    }

    // Get the access token
    fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    }

    // Get the user role
    fun getRole(): String? {
        return sharedPreferences.getString(ROLE_KEY, null)
    }

    // Clear the stored data
    fun clearData() {
        sharedPreferences.edit().clear().apply()
    }
}
