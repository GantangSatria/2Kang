package com.gsatria.a2kang.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gsatria.a2kang.core.util.TokenManager
import com.gsatria.a2kang.datasource.RetrofitClient
import com.gsatria.a2kang.datasource.repository.TukangHomeRepository
import com.gsatria.a2kang.model.response.TukangHomeResponse
import com.gsatria.a2kang.model.response.TukangProfileResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TukangHomeViewModel(application: Application) : AndroidViewModel(application) {

    private val tokenManager = TokenManager(application)
    private val repository = TukangHomeRepository(RetrofitClient.tukangApi)

    private val _homeData = MutableStateFlow<TukangHomeResponse?>(null)
    val homeData: StateFlow<TukangHomeResponse?> = _homeData

    private val _profileData = MutableStateFlow<TukangProfileResponse?>(null)
    val profileData: StateFlow<TukangProfileResponse?> = _profileData

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val token = tokenManager.getToken()
            if (token == null) {
                _error.value = "Token tidak ditemukan. Silakan login kembali."
                _loading.value = false
                return@launch
            }

            println("DEBUG: Loading tukang home data with token: ${token.take(20)}...")
            val result = repository.getTukangHome(token)

            if (result.isSuccess) {
                val data = result.getOrNull()
                println("DEBUG: Successfully loaded home data: $data")
                _homeData.value = data
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Gagal memuat data homepage"
                println("DEBUG: Error loading home data: $errorMsg")
                _error.value = errorMsg
            }

            _loading.value = false
        }
    }

    fun loadProfile() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val token = tokenManager.getToken()
            if (token == null) {
                _error.value = "Token tidak ditemukan. Silakan login kembali."
                _loading.value = false
                return@launch
            }

            val result = repository.getTukangProfile(token)

            if (result.isSuccess) {
                _profileData.value = result.getOrNull()
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Gagal memuat data profile"
            }

            _loading.value = false
        }
    }

    fun updateProfile(
        name: String? = null,
        bio: String? = null,
        services: String? = null,
        category: String? = null,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val token = tokenManager.getToken()
            if (token == null) {
                _error.value = "Token tidak ditemukan. Silakan login kembali."
                _loading.value = false
                return@launch
            }

            val request = com.gsatria.a2kang.model.request.UpdateTukangProfileRequest(
                name = name,
                bio = bio,
                services = services,
                category = category
            )

            val result = repository.updateTukangProfile(token, request)

            if (result.isSuccess) {
                // Reload profile after update
                loadProfile()
                loadHomeData() // Reload home data too
                onSuccess()
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Gagal update profile"
                _error.value = errorMsg
                onError(errorMsg)
            }

            _loading.value = false
        }
    }
}
