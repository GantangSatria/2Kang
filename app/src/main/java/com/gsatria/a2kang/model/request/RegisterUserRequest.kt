package com.gsatria.a2kang.model.request

data class RegisterUserRequest(
    val name: String,
    val email: String,
    val password: String
)
