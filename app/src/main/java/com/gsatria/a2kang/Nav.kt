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
import com.gsatria.a2kang.screen.user.homepage.HomepageUser
import com.gsatria.a2kang.screen.tukang.homepage.HomepageTukang
import com.gsatria.a2kang.viewmodel.auth.AuthViewModel
import com.gsatria.a2kang.viewmodel.auth.AuthViewModelFactory
import com.gsatria.a2kang.core.util.TokenManager
import com.gsatria.a2kang.datasource.TukangApi
import com.gsatria.a2kang.model.response.TukangResponse
import com.gsatria.a2kang.datasource.repository.TukangRepository
import com.gsatria.a2kang.model.domain.Tukang
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gsatria.a2kang.screen.user.homepage.component.TukangCard
import com.gsatria.a2kang.viewmodel.HomeViewModel

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
            val context = LocalContext.current
            val tokenManager = remember { TokenManager(context) }
            val repo = AuthRepository(RetrofitClient.authApi)
            val factory = AuthViewModelFactory(repo, tokenManager)
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
                },
                onLoginSuccess = { role ->
                    // Navigate berdasarkan role dari JWT token
                    when (role?.lowercase()) {
                        "user" -> {
                            navController.navigate("user_homepage") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                        "tukang" -> {
                            navController.navigate("tukang_homepage") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                        else -> {
                            // Default ke user homepage jika role tidak diketahui
                            navController.navigate("user_homepage") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }
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
            val factory = AuthViewModelFactory(repo, TokenManager(LocalContext.current))
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

        // User Homepage
        composable("user_homepage") {
            val viewModel = viewModel<HomeViewModel>()
            HomepageUser(
                viewModel = viewModel,
                onTukangClick = { tukangId ->
                    // Navigate to tukang detail if needed
                    // navController.navigate("tukang_detail/$tukangId")
                }
            )
        }

        // Tukang Homepage
        composable("tukang_homepage") {
            HomepageTukang(
                onEditProfileClick = {
                    // Navigate to edit profile if needed
                    // navController.navigate("edit_profile")
                },
                onJobClick = { jobId ->
                    // Navigate to job detail if needed
                    // navController.navigate("job_detail/$jobId")
                }
            )
        }
    }
}
