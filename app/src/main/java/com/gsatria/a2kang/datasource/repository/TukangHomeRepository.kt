package com.gsatria.a2kang.datasource.repository

import com.gsatria.a2kang.datasource.TukangApi
import com.gsatria.a2kang.model.response.TukangHomeResponse
import com.gsatria.a2kang.model.response.TukangProfileResponse
import com.google.gson.Gson

class TukangHomeRepository(
    private val api: TukangApi
) {
    private val gson = Gson()

    suspend fun getTukangHome(token: String): Result<TukangHomeResponse> {
        return try {
            val response = api.getTukangHome("Bearer $token")
            println("DEBUG: Response code: ${response.code()}")
            println("DEBUG: Response isSuccessful: ${response.isSuccessful}")

            if (response.isSuccessful) {

                val body = response.body()

                if (body != null) {
                    // Logging aman: Convert object body ke JSON string menggunakan Gson instance
                    println("DEBUG: Parsed Data: ${gson.toJson(body)}")

                    // Debugging spesifik properti
                    println("DEBUG: Profile prop: ${body.profile}")
                    println("DEBUG: Jobs prop: ${body.jobs}")

                    Result.success(body)
                } else {
                    println("DEBUG: Response body is null!")
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                // Error handling aman
                val errorBodyStr = response.errorBody()?.string() ?: "Unknown error"
                val errorMsg = "Gagal mengambil data: ${response.message()} ($errorBodyStr)"
                println("DEBUG: $errorMsg")
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            println("DEBUG: Exception in getTukangHome: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getTukangProfile(token: String): Result<TukangProfileResponse> {
        return try {
            val response = api.getTukangProfile("Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Gagal mengambil data profile: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateTukangProfile(
        token: String,
        request: com.gsatria.a2kang.model.request.UpdateTukangProfileRequest
    ): Result<Unit> {
        return try {
            val response = api.updateTukangProfile("Bearer $token", request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Gagal update profile: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}