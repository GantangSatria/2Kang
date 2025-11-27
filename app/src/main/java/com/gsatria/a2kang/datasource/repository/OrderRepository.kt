package com.gsatria.a2kang.datasource.repository

import android.util.Log
import com.gsatria.a2kang.datasource.OrderApi
import com.gsatria.a2kang.model.request.CreateOrderRequest
import com.gsatria.a2kang.model.response.OrderResponse

class OrderRepository(private val api: OrderApi) {

    suspend fun createOrder(token: String, request: CreateOrderRequest): Result<OrderResponse> {
        return try {
            val response = api.createOrder("Bearer $token", request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("Failed to create order: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMyOrders(token: String): Result<List<OrderResponse>> {
        return try {
            val response = api.getMyOrders("Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch orders"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getOrderDetail(token: String, id: Int): Result<OrderResponse> {
        return try {
            val response = api.getOrderDetail("Bearer $token", id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Order not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

        suspend fun getTukangOrders(token: String): Result<List<OrderResponse>> {
            return try {
                Log.d("OrderRepository", "Fetching tukang orders...")
                val response = api.getTukangOrders("Bearer $token")

                if (response.isSuccessful) {
                    val orders = response.body() ?: emptyList()
                    Log.d("OrderRepository", "Tukang orders fetched: ${orders.size} orders")
                    Result.success(orders)
                } else {
                    val errorMsg = "Error ${response.code()}: ${response.message()}"
                    Log.e("OrderRepository", errorMsg)
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                Log.e("OrderRepository", "Exception fetching tukang orders", e)
                Result.failure(e)
            }
        }

}