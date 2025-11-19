package com.gsatria.a2kang.di

import com.gsatria.a2kang.datasource.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {

    private var retrofit: Retrofit? = null
    private var apiService: ApiService? = null

    fun getRetrofit(): Retrofit {
        return retrofit ?: Retrofit.Builder()
            .baseUrl("http://10.0.2.2/api/v1")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .also { retrofit = it }
    }

    fun getApiService(): ApiService {
        return apiService ?: getRetrofit().create(ApiService::class.java)
            .also { apiService = it }
    }
}
