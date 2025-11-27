package com.gsatria.a2kang.datasource

import retrofit2.Response
import retrofit2.http.*

interface TukangOrderApi {
    @POST("tukang/order/{id}/accept")
    suspend fun acceptOrder(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Map<String, String>>

    @POST("tukang/order/{id}/start")
    suspend fun startOrder(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Map<String, String>>

    @POST("tukang/order/{id}/finish")
    suspend fun finishOrder(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Map<String, String>>

    @DELETE("tukang/order/{id}/reject")
    suspend fun rejectOrder(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Map<String, String>>
}