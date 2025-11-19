package com.gsatria.a2kang.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsatria.a2kang.datasource.repository.AuthRepository
import com.gsatria.a2kang.model.domain.User
import com.gsatria.a2kang.model.request.LoginRequest
import com.gsatria.a2kang.model.request.RegisterUserRequest
import com.gsatria.a2kang.model.request.RegisterTukangRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repo: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    private val _loggedUser = MutableStateFlow<String?>(null) // Simpan token
    val loggedUser: StateFlow<String?> = _loggedUser

    // =============================================
    // LOGIN
    // =============================================
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(loading = true)

            val result = repo.login(LoginRequest(email, password))

            if (result.isSuccess) {
                val token = result.getOrNull()
                _loggedUser.value = token
                _uiState.value = AuthUiState(successMessage = "Login berhasil!")
            } else {
                _uiState.value = AuthUiState(
                    errorMessage = result.exceptionOrNull()?.message ?: "Login gagal"
                )
            }
        }
    }

    // =============================================
    // REGISTER USER
    // =============================================
    fun registerUser(request: RegisterUserRequest) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(loading = true)

            val result = repo.registerUser(request)

            if (result.isSuccess) {
                _uiState.value = AuthUiState(successMessage = "Registrasi user berhasil!")
            } else {
                _uiState.value = AuthUiState(
                    errorMessage = result.exceptionOrNull()?.message ?: "Registrasi gagal"
                )
            }
        }
    }

    // =============================================
    // REGISTER TUKANG
    // =============================================
    fun registerTukang(request: RegisterTukangRequest) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(loading = true)

            val result = repo.registerTukang(request)

            if (result.isSuccess) {
                _uiState.value = AuthUiState(successMessage = "Registrasi tukang berhasil!")
            } else {
                _uiState.value = AuthUiState(
                    errorMessage = result.exceptionOrNull()?.message ?: "Registrasi gagal"
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = AuthUiState()
    }
}


