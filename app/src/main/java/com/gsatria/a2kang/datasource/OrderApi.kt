package com.gsatria.a2kang.datasource

import com.gsatria.a2kang.model.request.CreateOrderRequest
import com.gsatria.a2kang.model.response.OrderResponse
import retrofit2.Response
import retrofit2.http.*

interface OrderApi {
    @POST("orders")
    suspend fun createOrder(
        @Header("Authorization") token: String,
        @Body body: CreateOrderRequest
    ): Response<OrderResponse>

    @GET("orders")
    suspend fun getMyOrders(
        @Header("Authorization") token: String
    ): Response<List<OrderResponse>>

    @GET("orders/{id}")
    suspend fun getOrderDetail(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<OrderResponse>

    @GET("tukang/orders")
    suspend fun getTukangOrders(
        @Header("Authorization") token: String
    ): Response<List<OrderResponse>>
}