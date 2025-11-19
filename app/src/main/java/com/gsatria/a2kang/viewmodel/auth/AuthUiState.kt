package com.gsatria.a2kang.viewmodel.auth

data class AuthUiState(
    val loading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)
