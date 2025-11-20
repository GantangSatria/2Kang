package com.gsatria.a2kang.datasource

import com.gsatria.a2kang.model.request.UpdateTukangProfileRequest
import com.gsatria.a2kang.model.response.TukangHomeResponse
import com.gsatria.a2kang.model.response.TukangProfileResponse
import com.gsatria.a2kang.model.response.TukangResponse
import retrofit2.Response
import retrofit2.http.*

interface TukangApi {
    @GET("tukang")
    suspend fun getTukangList(
        @Header("Authorization") token: String,
        @Query("kategori") kategori: String? = null
    ): Response<List<TukangResponse>>

    @GET("tukang/home")
    suspend fun getTukangHome(
        @Header("Authorization") token: String
    ): Response<TukangHomeResponse>

    @GET("tukang/profile")
    suspend fun getTukangProfile(
        @Header("Authorization") token: String
    ): Response<TukangProfileResponse>

    @PATCH("tukang/profile")
    suspend fun updateTukangProfile(
        @Header("Authorization") token: String,
        @Body body: UpdateTukangProfileRequest
    ): Response<Map<String, String>>
}
