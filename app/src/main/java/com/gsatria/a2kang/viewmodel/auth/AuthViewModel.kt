package com.gsatria.a2kang.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsatria.a2kang.datasource.repository.AuthRepository
import com.gsatria.a2kang.model.request.LoginRequest
import com.gsatria.a2kang.model.request.RegisterRequest
import com.gsatria.a2kang.core.util.JwtUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repo: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole: StateFlow<String?> = _userRole

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(loading = true)

            val result = repo.login(LoginRequest(email, password))

            if (result.isSuccess) {
                val tokenValue = result.getOrNull()
                _token.value = tokenValue
                
                // Extract role dari JWT token
                tokenValue?.let { token ->
                    val role = JwtUtils.getRoleFromToken(token)
                    _userRole.value = role
                }
                
                _uiState.value = AuthUiState(successMessage = "Login berhasil!")
            } else {
                _uiState.value = AuthUiState(
                    errorMessage = result.exceptionOrNull()?.message ?: "Login gagal"
                )
            }
        }
    }

    // ============================
    // REGISTER USER
    // ============================
    fun registerUser(fullName: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(loading = true)

            val req = RegisterRequest(
                fullName = fullName,
                email = email,
                password = password,
                role = "user"
            )

            val result = repo.registerUser(req)

            if (result.isSuccess) {
                _uiState.value = AuthUiState(successMessage = "Registrasi user berhasil!")
            } else {
                _uiState.value = AuthUiState(
                    errorMessage = result.exceptionOrNull()?.message ?: "Registrasi gagal"
                )
            }
        }
    }

    // ============================
    // REGISTER TUKANG
    // ============================
    fun registerTukang(fullName: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(loading = true)

            val req = RegisterRequest(
                fullName = fullName,
                email = email,
                password = password,
                role = "tukang"
            )

            val result = repo.registerTukang(req)

            if (result.isSuccess) {
                _uiState.value = AuthUiState(successMessage = "Registrasi tukang berhasil!")
            } else {
                _uiState.value = AuthUiState(
                    errorMessage = result.exceptionOrNull()?.message ?: "Registrasi gagal"
                )
            }
        }
    }

    // ============================
    // RESET
    // ============================
    fun resetState() {
        _uiState.value = AuthUiState()
    }
}
