package com.towhidcse.swpc

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.towhidcse.swpc.ui.view.ForgotPasswordScreen
import com.towhidcse.swpc.ui.view.LoginScreen
import com.towhidcse.swpc.ui.view.PostScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Routes.MainRoute.Login.route) {
        composable(route = Routes.MainRoute.Login.route){
            LoginScreen(navController)
        }
        composable(route = Routes.MainRoute.ForgotPassword.route) {
            ForgotPasswordScreen()
        }
        composable(route = Routes.MainRoute.SignUp.route) {
            PostScreen()
        }
        composable(route = Routes.MainRoute.Home.route) {
            PostScreen()
        }
    }
}