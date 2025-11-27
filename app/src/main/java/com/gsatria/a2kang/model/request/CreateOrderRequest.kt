package com.gsatria.a2kang.model.request

import com.google.gson.annotations.SerializedName

data class CreateOrderRequest(
    @SerializedName("tukang_id")
    val tukangId: Int,
    @SerializedName("scheduled_at")
    val scheduledAt: String, // ISO8601 format (RFC3339)
    @SerializedName("address")
    val address: String,
    @SerializedName("payment_method")
    val paymentMethod: String, // "cash", "transfer", "qris"
    @SerializedName("price")
    val price: Double,
    @SerializedName("notes")
    val notes: String? = null
)