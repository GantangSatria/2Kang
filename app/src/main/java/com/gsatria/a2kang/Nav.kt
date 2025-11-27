package com.gsatria.a2kang

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gsatria.a2kang.core.util.TokenManager
import com.gsatria.a2kang.datasource.RetrofitClient
import com.gsatria.a2kang.datasource.repository.AuthRepository
import com.gsatria.a2kang.model.request.RegisterUserRequest
import com.gsatria.a2kang.screen.auth.LoginScreen
import com.gsatria.a2kang.screen.auth.SelectRoleScreen
import com.gsatria.a2kang.screen.auth.UploadVerifikasiTukangScreen
import com.gsatria.a2kang.screen.tukang.edit.EditProfileScreen
import com.gsatria.a2kang.screen.tukang.homepage.HomepageTukang
import com.gsatria.a2kang.screen.tukang.order.PermintaanScreen
import com.gsatria.a2kang.screen.tukang.profile.TukangProfileScreen
import com.gsatria.a2kang.screen.user.homepage.HomepageUser
import com.gsatria.a2kang.screen.user.order.OrderConfirmationScreen
import com.gsatria.a2kang.screen.welcome.WelcomeScreen
import com.gsatria.a2kang.viewmodel.HomeViewModel
import com.gsatria.a2kang.viewmodel.auth.AuthViewModel
import com.gsatria.a2kang.viewmodel.auth.AuthViewModelFactory
import com.gsatria.a2kang.viewmodel.TukangHomeViewModel
import com.gsatria.a2kang.screen.user.tukangdetail.TukangDetailScreen

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
                onNavigateToSelectRole = { registerReq: RegisterUserRequest ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("register_fullName", registerReq.full_name)
                    navController.currentBackStackEntry?.savedStateHandle?.set("register_email", registerReq.email)
                    navController.currentBackStackEntry?.savedStateHandle?.set("register_password", registerReq.password)
                    navController.navigate("select_role")
                },
                onLoginSuccess = { role ->
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
                            navController.navigate("user_homepage") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }
                }
            )
        }

        composable("select_role") {
            val fullName = navController.previousBackStackEntry?.savedStateHandle?.get<String>("register_fullName")
            val email = navController.previousBackStackEntry?.savedStateHandle?.get<String>("register_email")
            val password = navController.previousBackStackEntry?.savedStateHandle?.get<String>("register_password")
            val registerUserRequest = if (!fullName.isNullOrEmpty() && !email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                RegisterUserRequest(full_name = fullName, email = email, password = password)
            } else null

            val context = LocalContext.current
            val repo = AuthRepository(RetrofitClient.authApi)
            val factory = AuthViewModelFactory(repo, TokenManager(context))
            val viewModel = ViewModelProvider(LocalContext.current as ComponentActivity, factory)
                .get(AuthViewModel::class.java)

            SelectRoleScreen(
                onBackClick = { navController.popBackStack() },
                registerUserRequest = registerUserRequest,
                viewModel = viewModel,
                onRegisteredUser = {
                    navController.navigate("login") {
                        popUpTo("select_role") { inclusive = true }
                    }
                },
                onRegisteredTukang = {
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

        composable("user_homepage") {
            val viewModel = viewModel<HomeViewModel>()
            HomepageUser(
                viewModel = viewModel,
                onTukangClick = { tukangId ->
                    navController.navigate("tukang_detail/$tukangId")
                },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("homepage") { inclusive = true }
                    }
                }
            )
        }

        composable("tukang_homepage") {
            val homeViewModel = viewModel<TukangHomeViewModel>()
            HomepageTukang(
                viewModel = homeViewModel,
                onEditProfileClick = {
                    navController.navigate("tukang_profile")
                },
                onJobClick = { jobId ->
                    // Navigate to job detail if needed
                    // navController.navigate("job_detail/$jobId")
                },
                onPermintaanClick = {
                    navController.navigate("tukang_permintaan")
                }
            )
        }

        composable("tukang_profile") {
            val profileViewModel = viewModel<TukangHomeViewModel>()
            TukangProfileScreen(
                viewModel = profileViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onEditProfilClick = {
                    navController.navigate("edit_profile")
                }
            )
        }

        composable("edit_profile") {
            EditProfileScreen()
        }

        composable("tukang_detail/{tukangId}") { backStackEntry ->
            val tukangId = backStackEntry.arguments?.getString("tukangId")?.toIntOrNull() ?: 0
            TukangDetailScreen(
                tukangId = tukangId,
                onBackClick = {
                    navController.popBackStack()
                },
                onBookServiceClick = {
                    navController.navigate("order_confirmation/$tukangId")
                }
            )
        }

        composable("order_confirmation/{tukangId}") { backStackEntry ->
            val tukangId = backStackEntry.arguments?.getString("tukangId")?.toIntOrNull() ?: 0
            OrderConfirmationScreen(
                tukangId = tukangId,
                onBackClick = {
                    navController.popBackStack()
                },
                onOrderSuccess = {
                    // Navigate to success screen or back to homepage
                    navController.navigate("user_homepage") {
                        popUpTo("tukang_detail") { inclusive = true }
                    }
                }
            )
        }

        composable("tukang_permintaan") {
            PermintaanScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
