package com.gsatria.a2kang.model.request

data class RegisterUserRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val role: String = "user"
)