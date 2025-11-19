package com.gsatria.a2kang.model.response

data class TukangResponse(
    val id: Int,
    val name: String,
    val email: String,
    val category: String,
    val bio: String,
    val services: String,
    val rating: Float,
    val createdAt: String
)
