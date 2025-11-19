package com.gsatria.a2kang.model.domain

data class Tukang(
    val id: Int,
    val name: String,
    val email: String,
    val password: String? = null,
    val category: List<String>,
    val bio: String,
    val services: String,
    val rating: Float,
    val createdAt: String,
    val price: Int
)
