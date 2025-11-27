package com.gsatria.a2kang.datasource.repository

import com.gsatria.a2kang.datasource.OrderApi
import com.gsatria.a2kang.datasource.TukangOrderApi
import com.gsatria.a2kang.model.response.OrderResponse

class TukangOrderRepository(
    private val orderApi: OrderApi,
    private val tukangOrderApi: TukangOrderApi
) {

    suspend fun getTukangOrders(token: String): Result<List<OrderResponse>> {
        return try {
            val response = orderApi.getTukangOrders("Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("Failed to fetch orders: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun acceptOrder(token: String, orderId: Int): Result<String> {
        return try {
            val response = tukangOrderApi.acceptOrder("Bearer $token", orderId)
            if (response.isSuccessful) {
                val message = response.body()?.get("message") ?: "Order accepted"
                Result.success(message)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("Failed to accept order: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun startOrder(token: String, orderId: Int): Result<String> {
        return try {
            val response = tukangOrderApi.startOrder("Bearer $token", orderId)
            if (response.isSuccessful) {
                val message = response.body()?.get("message") ?: "Order started"
                Result.success(message)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("Failed to start order: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun finishOrder(token: String, orderId: Int): Result<String> {
        return try {
            val response = tukangOrderApi.finishOrder("Bearer $token", orderId)
            if (response.isSuccessful) {
                val message = response.body()?.get("message") ?: "Order finished"
                Result.success(message)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("Failed to finish order: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun rejectOrder(token: String, orderId: Int): Result<String> {
        return try {
            val response = tukangOrderApi.rejectOrder("Bearer $token", orderId)
            if (response.isSuccessful) {
                val message = response.body()?.get("message") ?: "Order rejected"
                Result.success(message)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("Failed to reject order: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}