package com.gsatria.a2kang.model.request

data class RegisterTukangRequest(
    val full_name: String,
    val email: String,
    val password: String
)
