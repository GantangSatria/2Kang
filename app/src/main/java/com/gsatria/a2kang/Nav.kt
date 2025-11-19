package com.gsatria.a2kang

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
//        composable("home") { HomeScreen(navController) }
//        composable("detail") { DetailScreen() }
    }
}