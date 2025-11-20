package com.gsatria.a2kang.model.response

import com.google.gson.annotations.SerializedName

data class TukangResponse(
    @SerializedName("ID")
    val id: Int,
    @SerializedName("Name")
    val name: String? = null,
    @SerializedName("Email")
    val email: String? = null,
    @SerializedName("Category")
    val category: String? = null,
    @SerializedName("Bio")
    val bio: String? = null,
    @SerializedName("Services")
    val services: String? = null,
    @SerializedName("Rating")
    val rating: Float? = null,
    @SerializedName("CreatedAt")
    val createdAt: String? = null
)
