package com.gsatria.a2kang.datasource

import com.gsatria.a2kang.model.request.LoginRequest
import com.gsatria.a2kang.model.request.RegisterRequest
import com.gsatria.a2kang.model.request.RegisterUserRequest
import com.gsatria.a2kang.model.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("register")
    suspend fun register(@Body body: RegisterRequest): Response<AuthResponse>

    @POST("login")
    suspend fun login(@Body body: LoginRequest): Response<AuthResponse>
}
