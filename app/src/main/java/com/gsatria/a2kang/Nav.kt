package com.gsatria.a2kang

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gsatria.a2kang.datasource.RetrofitClient
import com.gsatria.a2kang.datasource.repository.AuthRepository
import com.gsatria.a2kang.screen.auth.LoginScreen
import com.gsatria.a2kang.screen.auth.SelectRoleScreen
import com.gsatria.a2kang.model.request.RegisterUserRequest
import com.gsatria.a2kang.screen.auth.UploadVerifikasiTukangScreen
import com.gsatria.a2kang.screen.welcome.WelcomeScreen
import com.gsatria.a2kang.viewmodel.auth.AuthViewModel
import com.gsatria.a2kang.viewmodel.auth.AuthViewModelFactory

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable("welcome") {
            WelcomeScreen(
                onStartClick = {
                    navController.navigate("login")
                }
            )
        }

        composable("login") {
            val repo = AuthRepository(RetrofitClient.authApi)
            val factory = AuthViewModelFactory(repo)
            val viewModel = ViewModelProvider(LocalContext.current as ComponentActivity, factory)
                .get(AuthViewModel::class.java)
            LoginScreen(
                viewModel = viewModel,
                onNavigateToSelectRole = { registerReq ->
                    // store fields in savedStateHandle, then navigate
                    navController.currentBackStackEntry?.savedStateHandle?.set("register_fullName", registerReq.full_name)
                    navController.currentBackStackEntry?.savedStateHandle?.set("register_email", registerReq.email)
                    navController.currentBackStackEntry?.savedStateHandle?.set("register_password", registerReq.password)
                    navController.navigate("select_role")
                }
            )
        }

        composable("select_role") {
            // read saved register fields from previous entry
            val fullName = navController.previousBackStackEntry?.savedStateHandle?.get<String>("register_fullName")
            val email = navController.previousBackStackEntry?.savedStateHandle?.get<String>("register_email")
            val password = navController.previousBackStackEntry?.savedStateHandle?.get<String>("register_password")
            val registerUserRequest = if (!fullName.isNullOrEmpty() && !email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                RegisterUserRequest(full_name = fullName, email = email, password = password)
            } else null

            val repo = AuthRepository(RetrofitClient.authApi)
            val factory = AuthViewModelFactory(repo)
            val viewModel = ViewModelProvider(LocalContext.current as ComponentActivity, factory)
                .get(AuthViewModel::class.java)

            SelectRoleScreen(
                onBackClick = { navController.popBackStack() },
                registerUserRequest = registerUserRequest,
                viewModel = viewModel,
                onRegisteredUser = {  // PENGGUNA
                    navController.navigate("login") {
                        popUpTo("select_role") { inclusive = true }
                    }
                },
                onRegisteredTukang = {  // TUKANG
                    navController.navigate("upload_verifikasi_tukang") {
                        popUpTo("select_role") { inclusive = true }
                    }
                }
            )

        }

        composable("upload_verifikasi_tukang"){
            UploadVerifikasiTukangScreen(
                onBackClick = { navController.popBackStack() },
                onSaveAndContinue = {
                    navController.navigate("login") {
                        popUpTo("upload_verifikasi_tukang") { inclusive = true }
                    }
                }
            )
        }
    }
}
