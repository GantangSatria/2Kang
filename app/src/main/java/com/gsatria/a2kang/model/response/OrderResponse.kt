package com.gsatria.a2kang.model.response

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("tukang_id")
    val tukangId: Int,
    @SerializedName("scheduled_at")
    val scheduledAt: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("payment_method")
    val paymentMethod: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("status")
    val status: String,
    @SerializedName("notes")
    val notes: String?,
    @SerializedName("created_at")
    val createdAt: String
)