package com.gsatria.a2kang.datasource.repository

import com.gsatria.a2kang.datasource.AuthApi
import com.gsatria.a2kang.model.request.LoginRequest
import com.gsatria.a2kang.model.request.RegisterTukangRequest
import com.gsatria.a2kang.model.request.RegisterUserRequest

class AuthRepository(private val api: AuthApi) {
    suspend fun registerUser(req: RegisterUserRequest): Result<Unit> {
        return try {
            val body = mapOf(
                "fullName" to req.fullName,
                "email" to req.email,
                "password" to req.password,
                "role" to req.role
            )
            val res = api.register(body)

            if (res.isSuccessful) Result.success(Unit)
            else Result.failure(Exception(res.errorBody()?.string()))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun registerTukang(req: RegisterTukangRequest): Result<Unit> {
        return try {
            val body = mapOf(
                "fullName" to req.fullName,
                "email" to req.email,
                "password" to req.password,
                "role" to req.role
            )
            val res = api.register(body)

            if (res.isSuccessful) Result.success(Unit)
            else Result.failure(Exception(res.errorBody()?.string()))
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