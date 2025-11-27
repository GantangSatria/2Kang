package com.gsatria.a2kang.datasource.repository

import com.gsatria.a2kang.datasource.TukangApi
import com.gsatria.a2kang.model.domain.Tukang
import com.gsatria.a2kang.model.response.TukangResponse
import com.google.gson.Gson

class TukangRepository(
    private val api: TukangApi
) {
    private val gson = Gson()

    suspend fun getTukangList(token: String, kategori: String? = null): Result<List<Tukang>> {
        return try {
            val response = api.getTukangList("Bearer $token", kategori)
            if (response.isSuccessful && response.body() != null) {
                val responseBody = response.body()!!
                println("DEBUG: Response body size: ${responseBody.size}")

                // Log JSON dari response body yang sudah dikonversi untuk debugging
                if (responseBody.isNotEmpty()) {
                    val jsonString = gson.toJson(responseBody)
                    println("DEBUG: Response JSON: $jsonString")

                    val firstItem = responseBody[0]
                    // UPDATE: Menghapus log email karena fieldnya tidak ada
                    println("DEBUG: First item - id: ${firstItem.id}, name: '${firstItem.name}'")
                    println("DEBUG: First item JSON: ${gson.toJson(firstItem)}")
                }

                val tukangList = responseBody
                    .mapNotNull { responseItem ->
                        try {
                            val domain = responseItem.toDomain()
                            if (domain == null) {
                                println("DEBUG: Skipped tukang with id: ${responseItem.id}")
                            }
                            domain
                        } catch (e: Exception) {
                            e.printStackTrace()
                            println("DEBUG: Error converting tukang: ${e.message}")
                            null
                        }
                    }

                println("DEBUG: Successfully converted ${tukangList.size} tukangs")
                Result.success(tukangList)
            } else {
                val errorMsg = "Gagal mengambil data tukang: ${response.message()}, Code: ${response.code()}"
                println("DEBUG: $errorMsg")
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            println("DEBUG: Exception in getTukangList: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getTukangDetail(token: String, id: Int): Result<com.gsatria.a2kang.model.domain.Tukang> {
        return try {
            val response = api.getTukangDetail("Bearer $token", id)
            if (response.isSuccessful && response.body() != null) {
                val responseItem = response.body()!!
                val domain = responseItem.toDomain()
                if (domain != null) {
                    Result.success(domain)
                } else {
                    Result.failure(Exception("Gagal mengkonversi data tukang"))
                }
            } else {
                Result.failure(Exception("Gagal mengambil detail tukang: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// Extension function untuk convert TukangResponse ke Tukang domain
private fun TukangResponse.toDomain(): Tukang? {
    // Validasi id - harus ada dan > 0
    val tukangId = id?.takeIf { it > 0 } ?: run {
        println("DEBUG: Invalid id: $id")
        return null
    }

    // Name bisa null atau blank, beri default value
    val tukangName = name?.takeIf { it.isNotBlank() }
        ?: "Tukang #$tukangId"


    return Tukang(
        id = tukangId,
        name = tukangName,
        email = "", // Default kosong karena tidak diambil dari API
        category = category ?: "",
        bio = bio ?: "",
        services = services ?: "",
        rating = rating ?: 0f,
        createdAt = "" // Default kosong karena tidak diambil dari API
    )
}