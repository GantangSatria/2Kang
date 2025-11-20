package com.gsatria.a2kang.model.response

import com.google.gson.annotations.SerializedName

data class TukangProfileResponse(
    @SerializedName("Profile")
    val profile: TukangResponse,
    @SerializedName("Reviews")
    val reviews: List<ReviewResponse>? = null
)

data class ReviewResponse(
    @SerializedName("ID")
    val id: Int? = null,
    @SerializedName("Name")
    val name: String? = null,
    @SerializedName("Rating")
    val rating: Float? = null,
    @SerializedName("Comment")
    val comment: String? = null
)
