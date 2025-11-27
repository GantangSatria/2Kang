package com.gsatria.a2kang.model.response

import com.google.gson.annotations.SerializedName

// Class ini digunakan untuk membungkus semua response dari backend
data class BaseResponse<T>(
    @SerializedName("message")
    val message: String? = null,

    @SerializedName("data")
    val data: T? = null
)