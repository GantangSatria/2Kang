package com.gsatria.a2kang.datasource

import com.gsatria.a2kang.model.request.LoginRequest
import com.gsatria.a2kang.model.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("register")
    suspend fun register(@Body body: Map<String, String>): Response<Map<String, Any>>

    @POST("login")
    suspend fun login(@Body body: LoginRequest): Response<AuthResponse>
}