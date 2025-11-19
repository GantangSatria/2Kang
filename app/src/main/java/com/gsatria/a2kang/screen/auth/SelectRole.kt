package com.gsatria.a2kang.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gsatria.a2kang.R
import com.gsatria.a2kang.ui.theme.SoraFontFamily
import com.gsatria.a2kang.viewmodel.auth.AuthViewModel
import com.gsatria.a2kang.model.request.RegisterUserRequest

enum class UserRole { PENGGUNA, TUKANG }

@Composable
fun SelectRoleScreen(
    onBackClick: () -> Unit = {},
    registerUserRequest: RegisterUserRequest? = null,
    viewModel: AuthViewModel,
    onRegistered: () -> Unit = {}
) {
    var selectedRole by remember { mutableStateOf<UserRole?>(null) }
    val blueColor = Color(0xFF2D8CFF)
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
            onRegistered()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SelectRoleTopBar(onBackClick)

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Spacer(modifier = Modifier.height(32.dp))
                SelectRoleHeadline()
                Spacer(modifier = Modifier.height(32.dp))
            }


            RoleCard(
                role = UserRole.PENGGUNA,
                title = "Pengguna",
                description = "Anda menjadi pengguna yang dapat memilih tukang tepercaya untuk membantu memperbaiki berbagai kerusakan di rumah Anda.",
                userImageResId = R.drawable.ic_roleuser,
                isSelected = selectedRole == UserRole.PENGGUNA,
                onCardClick = { selectedRole = UserRole.PENGGUNA }
            )

            Spacer(modifier = Modifier.height(36.dp))

            RoleCard(
                role = UserRole.TUKANG,
                title = "Tukang",
                description = "Anda menjadi tukang yang dapat menawarkan keahlian dan menerima pekerjaan dari pengguna yang membutuhkan bantuan Anda.",
                userImageResId = R.drawable.ic_roletukang,
                isSelected = selectedRole == UserRole.TUKANG,
                onCardClick = { selectedRole = UserRole.TUKANG }
            )

            Spacer(modifier = Modifier.height(48.dp))

            PrimaryActionButton(
                text = "Selanjutnya",
                enabled = selectedRole != null,
                blueColor = blueColor,
                onClick = {
                    // perform registration using viewModel and the saved registerUserRequest
                    val req = registerUserRequest
                    if (req != null && selectedRole != null) {
                        when (selectedRole) {
                            UserRole.PENGGUNA -> viewModel.registerUser(req.full_name, req.email, req.password)
                            UserRole.TUKANG -> viewModel.registerTukang(req.full_name, req.email, req.password)
                            else -> {}
                        }
                    }
                },
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Box(Modifier.fillMaxWidth()) {
                SnackbarHost(snackbarHostState, Modifier.align(Alignment.Center))
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// -------------------------------------------------------------------------

@Composable
fun RoleCard(
    role: UserRole,
    title: String,
    description: String,
    userImageResId: Int,
    isSelected: Boolean,
    onCardClick: () -> Unit
) {
    val borderColor = if (isSelected) Color(0xFF1E80FF) else Color(0xFFD9D9D9)

    Box(
        modifier = Modifier
            .width(320.dp)
            .height(150.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(10.dp))
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(10.dp))
            .clickable(onClick = onCardClick)
            .padding(1.dp)
    ) {
        Row(
            modifier = Modifier
                .width(297.5.dp)
                .height(120.dp)
                .align(Alignment.Center)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = userImageResId),
                contentDescription = title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF000000),
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF000000),
                    )
                )
            }
        }
    }
}

// -------------------------------------------------------------------------

@Composable
fun SelectRoleTopBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = "Kembali",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable(onClick = onBackClick)
                .size(24.dp)
        )

        Text(
            text = "Pilih role kamu",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = SoraFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF000000),
            )
        )
    }
}

// -------------------------------------------------------------------------

@Composable
fun SelectRoleHeadline() {
    Text(
        text = "Siap mulai?",
        style = TextStyle(
            fontSize = 28.sp,
            fontFamily = SoraFontFamily,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black
        )
    )
    Text(
        text = "Pilih peranmu di 2Kang",
        style = TextStyle(
            fontSize = 28.sp,
            fontFamily = SoraFontFamily,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black
        )
    )
}


@Composable
fun PrimaryActionButton(text: String, enabled: Boolean, blueColor: Color, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = blueColor,
            disabledContainerColor = blueColor.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(10.dp),
        enabled = enabled
    ) {
        Text(
            text,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = SoraFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSelectRoleScreen() {
    // Preview without ViewModel injection
}