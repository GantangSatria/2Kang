package com.gsatria.a2kang.model.request

data class RegisterTukangRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val role: String = "tukang"
)
