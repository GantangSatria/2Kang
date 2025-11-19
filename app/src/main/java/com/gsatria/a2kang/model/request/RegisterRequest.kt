package com.gsatria.a2kang.model.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("full_name")
    val fullName: String,
    val email: String,
    val password: String,
    val role: String // "user" atau "tukang"
)
