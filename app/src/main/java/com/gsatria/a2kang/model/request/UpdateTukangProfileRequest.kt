package com.gsatria.a2kang.model.request

import com.google.gson.annotations.SerializedName

data class UpdateTukangProfileRequest(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("bio")
    val bio: String? = null,
    @SerializedName("services")
    val services: String? = null,
    @SerializedName("category")
    val category: String? = null
)
