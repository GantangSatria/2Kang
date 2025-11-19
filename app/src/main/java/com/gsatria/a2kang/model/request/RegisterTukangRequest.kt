package com.gsatria.a2kang.model.request

data class RegisterTukangRequest(
    val name: String,
    val email: String,
    val password: String,
    val category: String,
    val bio: String,
    val services: String
)
