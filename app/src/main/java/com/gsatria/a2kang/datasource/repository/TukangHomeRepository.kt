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

            if (response.isSuccessful && response.body() != null) {
                // 1. Ambil BaseResponse
                val baseResponse = response.body()!!

                // 2. Ambil data aslinya (TukangHomeResponse)
                val homeData = baseResponse.data

                if (homeData != null) {
                    println("DEBUG: Parsed Data Success!")
                    println("DEBUG: Profile Name: ${homeData.profile?.name}")
                    Result.success(homeData)
                } else {
                    println("DEBUG: Field 'data' is null in response")
                    Result.failure(Exception("Data kosong dari server"))
                }
            } else {
                val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                println("DEBUG: Error - $errorMsg")
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            println("DEBUG: Exception - ${e.message}")
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