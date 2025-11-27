package com.gsatria.a2kang.datasource.repository

import com.gsatria.a2kang.datasource.AuthApi
import com.gsatria.a2kang.model.request.LoginRequest
import com.gsatria.a2kang.model.request.RegisterRequest

class AuthRepository(private val api: AuthApi) {

    suspend fun registerUser(req: RegisterRequest): Result<String> {
        return try {
            val res = api.register(req)
            if (res.isSuccessful && res.body() != null) {
                Result.success(res.body()!!.token)
            } else {
                Result.failure(Exception("Registrasi gagal: ${res.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun registerTukang(req: RegisterRequest): Result<String> {
        return try {
            val res = api.register(req)
            if (res.isSuccessful && res.body() != null) {
                Result.success(res.body()!!.token)
            } else {
                Result.failure(Exception("Registrasi tukang gagal: ${res.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(req: LoginRequest): Result<String> {
        return try {
            val res = api.login(req)
            if (res.isSuccessful && res.body() != null) {
                Result.success(res.body()!!.token)
            } else {
                Result.failure(Exception("Email atau password salah"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}