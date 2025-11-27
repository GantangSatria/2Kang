package com.gsatria.a2kang.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gsatria.a2kang.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import com.gsatria.a2kang.model.request.RegisterUserRequest
import com.gsatria.a2kang.viewmodel.auth.AuthViewModel
import com.gsatria.a2kang.viewmodel.auth.AuthUiState

val sora = FontFamily(
    Font(R.font.sora_regular, FontWeight.Normal),
    Font(R.font.sora_bold, FontWeight.Bold),
    Font(R.font.sora_extrabold, FontWeight.ExtraBold)
)
enum class AuthTab { Masuk, Daftar }

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onNavigateToSelectRole: (RegisterUserRequest) -> Unit = {},
    onLoginSuccess: (String?) -> Unit = {} // Callback dengan role
) {
    var selectedTab by remember { mutableStateOf(AuthTab.Masuk) }

    var loginEmail by remember { mutableStateOf("") }
    var loginPassword by remember { mutableStateOf("") }

    var registerUsername by remember { mutableStateOf("") }
    var registerEmail by remember { mutableStateOf("") }
    var registerPassword by remember { mutableStateOf("") }
    var registerConfirmPassword by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.resetState()
        }
        uiState.successMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.resetState()
            // Panggil callback dengan role setelah login berhasil
            val role = viewModel.userRole.value
            onLoginSuccess(role)
        }
    }

    Box(Modifier.fillMaxSize()) {
        SnackbarHost(snackbarHostState, Modifier.align(Alignment.BottomCenter))
    }

    if (uiState.loading) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Loading...") },
            confirmButton = {}
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(60.dp))

        LogoAndAppName()

        Spacer(modifier = Modifier.height(48.dp))

        LoginRegisterTabs(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        AuthForm(
            selectedTab = selectedTab,
            sora = sora,
            loginEmail = loginEmail,
            onLoginEmailChange = { loginEmail = it },
            loginPassword = loginPassword,
            onLoginPasswordChange = { loginPassword = it },
            registerUsername = registerUsername,
            onRegisterUsernameChange = { registerUsername = it },
            registerEmail = registerEmail,
            onRegisterEmailChange = { registerEmail = it },
            registerPassword = registerPassword,
            onRegisterPasswordChange = { registerPassword = it },
            registerConfirmPassword = registerConfirmPassword,
            onRegisterConfirmPasswordChange = { registerConfirmPassword = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryActionButton(
            sora = sora,
            selectedTab = selectedTab,
            loginEmail = loginEmail,
            loginPassword = loginPassword,
            registerUsername = registerUsername,
            registerEmail = registerEmail,
            registerPassword = registerPassword,
            registerConfirmPassword = registerConfirmPassword,
            viewModel = viewModel,
            onNavigateToSelectRole = onNavigateToSelectRole
        )

        Spacer(modifier = Modifier.height(64.dp))
    }
}

// -------------------------------------------------------------------------

@Composable
private fun AuthForm(
    selectedTab: AuthTab,
    sora: FontFamily,
    loginEmail: String,
    onLoginEmailChange: (String) -> Unit,
    loginPassword: String,
    onLoginPasswordChange: (String) -> Unit,
    registerUsername: String,
    onRegisterUsernameChange: (String) -> Unit,
    registerEmail: String,
    onRegisterEmailChange: (String) -> Unit,
    registerPassword: String,
    onRegisterPasswordChange: (String) -> Unit,
    registerConfirmPassword: String,
    onRegisterConfirmPasswordChange: (String) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (selectedTab == AuthTab.Masuk) {

            AuthInputField(
                value = loginEmail,
                onValueChange = onLoginEmailChange,
                hint = "abc@email.com",
                label = "Email",
                icon = Icons.Default.Email,
                sora = sora
            )

            Spacer(modifier = Modifier.height(16.dp))

            AuthInputField(
                value = loginPassword,
                onValueChange = onLoginPasswordChange,
                hint = "Password Anda",
                label = "Password",
                icon = Icons.Default.Lock,
                sora = sora,
                isPassword = true
            )

            Row(
                modifier = Modifier.width(304.dp).padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Remember Me", style = TextStyle(fontFamily = sora, color = Color.Gray, fontSize = 12.sp))
                Text("Forgot Password?", style = TextStyle(fontFamily = sora, color = Color.Gray, fontSize = 12.sp))
            }

        } else {

            AuthInputField(
                value = registerUsername,
                onValueChange = onRegisterUsernameChange,
                hint = "Masukkan Username",
                label = "Username",
                icon = Icons.Default.Person,
                sora = sora
            )
            Spacer(modifier = Modifier.height(16.dp))

            AuthInputField(
                value = registerEmail,
                onValueChange = onRegisterEmailChange,
                hint = "abc@email.com",
                label = "Email",
                icon = Icons.Default.Email,
                sora = sora
            )
            Spacer(modifier = Modifier.height(16.dp))

            AuthInputField(
                value = registerPassword,
                onValueChange = onRegisterPasswordChange,
                hint = "Buat Password",
                label = "Password",
                icon = Icons.Default.Lock,
                sora = sora,
                isPassword = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            AuthInputField(
                value = registerConfirmPassword,
                onValueChange = onRegisterConfirmPasswordChange,
                hint = "Konfirmasi Password",
                label = "Konfirmasi Password",
                icon = Icons.Default.Lock,
                sora = sora,
                isPassword = true
            )
        }
    }
}

@Composable
fun AuthInputField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    sora: FontFamily,
    isPassword: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.width(304.dp),
        leadingIcon = { Icon(icon, contentDescription = label) },
        shape = RoundedCornerShape(10.dp),
        placeholder = { Text(hint, style = TextStyle(fontFamily = sora, fontSize = 16.sp, color = Color.Gray)) },
        textStyle = TextStyle(fontFamily = sora, fontSize = 16.sp, color = Color.Black),
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = if (isPassword) {
            {
                val iconRes = if (passwordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painterResource(id = iconRes), contentDescription = "Toggle password visibility")
                }
            }
        } else null,
    )
}

// -------------------------------------------------------------------------

@Composable
fun LogoAndAppName() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_2kang),
            contentDescription = "Logo 2Kang",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "2Kang",
            style = TextStyle(
                fontSize = 36.sp,
                fontFamily = sora,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
            )
        )
    }
}

@Composable
fun LoginRegisterTabs(
    selectedTab: AuthTab,
    onTabSelected: (AuthTab) -> Unit
) {
    Row(
        modifier = Modifier
            .width(304.dp)
            .height(48.dp)
            .background(
                color = Color(0x40D9D9D9),
                shape = RoundedCornerShape(size = 100.dp)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TabItem(
            text = "Masuk",
            isSelected = selectedTab == AuthTab.Masuk,
            onClick = { onTabSelected(AuthTab.Masuk) }
        )
        TabItem(
            text = "Daftar",
            isSelected = selectedTab == AuthTab.Daftar,
            onClick = { onTabSelected(AuthTab.Daftar) }
        )
    }
}

@Composable
fun TabItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFFFFFFFF) else Color(0x80D9D9D9)
    val textColor = if (isSelected) Color(0xFF000000) else Color(0xFFD9D9D9)

    Row(
        modifier = Modifier
            .width(140.dp)
            .height(40.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(size = 100.dp)
            )
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = sora,
                fontWeight = FontWeight.Normal,
                color = textColor,
            )
        )
    }
}

@Composable
fun PrimaryActionButton(
    sora: FontFamily,
    selectedTab: AuthTab,
    loginEmail: String,
    loginPassword: String,
    registerUsername: String,
    registerEmail: String,
    registerPassword: String,
    registerConfirmPassword: String,
    viewModel: AuthViewModel,
    onNavigateToSelectRole: (RegisterUserRequest) -> Unit = {}
) {
    Button(
        onClick = {
            if (selectedTab == AuthTab.Masuk) {
                // Login — sesuai signature viewModel.login(email, password)
                viewModel.login(loginEmail, loginPassword)

            } else {
                // Register — pastikan password cocok dulu
                if (registerPassword != registerConfirmPassword) {
                    // pakai snackbar / toast lebih baik, sekarang debug:
                    println("Password tidak cocok")
                    return@Button
                }

                // Instead of registering immediately, pass the filled form to SelectRole
                val registerReq = RegisterUserRequest(
                    full_name = registerUsername,
                    email = registerEmail,
                    password = registerPassword
                )
                onNavigateToSelectRole(registerReq)
            }
        },
        modifier = Modifier
            .width(304.dp)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF2D8CFF)
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            if (selectedTab == AuthTab.Masuk) "Masuk" else "Daftar",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = sora,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoginScreen() {
    // Preview is commented out because it requires AuthViewModel
    // To preview, you need to provide a mock ViewModel
    // LoginScreen(viewModel = mockViewModel)
}