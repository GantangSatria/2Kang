package com.gsatria.a2kang.datasource

import com.gsatria.a2kang.model.response.TukangResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TukangApi {
    @GET("tukang")
    suspend fun getTukangList(
        @Header("Authorization") token: String,
        @Query("kategori") kategori: String? = null
    ): Response<List<TukangResponse>>
}
