package com.gsatria.a2kang.viewmodel.auth

data class AuthUiState(
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
