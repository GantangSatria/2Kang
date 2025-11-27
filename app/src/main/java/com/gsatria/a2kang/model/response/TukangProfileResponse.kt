package com.gsatria.a2kang.model.response

import com.google.gson.annotations.SerializedName

data class TukangProfileResponse(
    @SerializedName("profile")
    val profile: TukangResponse,

    @SerializedName("reviews")
    val reviews: List<ReviewResponse>
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
