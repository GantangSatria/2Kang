package com.gsatria.a2kang.model.domain

data class Tukang(
    val id: Int,
    val name: String? = null,
    val email: String? = null,
    val category: String? = null,
    val bio: String? = null,
    val services: String? = null,
    val rating: Float? = null,
    val createdAt: String? = null
)
