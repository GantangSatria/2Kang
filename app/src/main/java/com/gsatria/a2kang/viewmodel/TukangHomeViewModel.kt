package com.gsatria.a2kang.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gsatria.a2kang.core.util.TokenManager
import com.gsatria.a2kang.datasource.RetrofitClient
import com.gsatria.a2kang.datasource.repository.OrderRepository
import com.gsatria.a2kang.datasource.repository.TukangHomeRepository
import com.gsatria.a2kang.model.request.UpdateTukangProfileRequest
import com.gsatria.a2kang.model.response.OrderResponse
import com.gsatria.a2kang.model.response.TukangProfileResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TukangHomeViewModel(application: Application) : AndroidViewModel(application) {
    private val tokenManager = TokenManager(application)
    private val tukangRepository = TukangHomeRepository(RetrofitClient.tukangApi)
    private val orderRepository = OrderRepository(RetrofitClient.orderApi)

    private val _profileData = MutableStateFlow<TukangProfileResponse?>(null)
    val profileData: StateFlow<TukangProfileResponse?> = _profileData

    private val _ordersData = MutableStateFlow<List<OrderResponse>?>(null)
    val ordersData: StateFlow<List<OrderResponse>?> = _ordersData

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadProfileAndOrders() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val token = tokenManager.getToken()
            if (token == null) {
                _error.value = "Token tidak ditemukan. Silakan login ulang."
                _loading.value = false
                return@launch
            }

            // Load profile
            val profileResult = tukangRepository.getTukangProfile(token)
            profileResult.fold(
                onSuccess = { profile ->
                    _profileData.value = profile
                    Log.d("TukangHomeVM", "Profile loaded: ${profile.profile.name}")
                },
                onFailure = { e ->
                    _error.value = "Gagal memuat profil: ${e.message}"
                    Log.e("TukangHomeVM", "Failed to load profile", e)
                }
            )

            // Load orders
            val ordersResult = orderRepository.getTukangOrders(token)
            ordersResult.fold(
                onSuccess = { orders ->
                    _ordersData.value = orders
                    Log.d("TukangHomeVM", "Orders loaded: ${orders.size} orders")
                },
                onFailure = { e ->
                    // Orders bisa kosong, jangan set error
                    _ordersData.value = emptyList()
                    Log.w("TukangHomeVM", "Failed to load orders: ${e.message}")
                }
            )

            _loading.value = false
        }
    }

    fun loadProfile() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val token = tokenManager.getToken()
            if (token == null) {
                _error.value = "Token tidak ditemukan. Silakan login ulang."
                _loading.value = false
                return@launch
            }

            val result = tukangRepository.getTukangProfile(token)
            result.fold(
                onSuccess = { profile ->
                    _profileData.value = profile
                    Log.d("TukangHomeVM", "Profile loaded successfully")
                },
                onFailure = { e ->
                    _error.value = "Gagal memuat data: ${e.message}"
                    Log.e("TukangHomeVM", "Failed to load profile", e)
                }
            )

            _loading.value = false
        }
    }

    fun updateProfile(name: String?, bio: String?, services: String?, category: String?) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val token = tokenManager.getToken()
            if (token == null) {
                _error.value = "Token tidak ditemukan"
                _loading.value = false
                return@launch
            }

            val request = UpdateTukangProfileRequest(
                name = name,
                bio = bio,
                services = services,
                category = category
            )

            val result = tukangRepository.updateTukangProfile(token, request)
            result.fold(
                onSuccess = {
                    Log.d("TukangHomeVM", "Profile updated successfully")
                    loadProfile()
                },
                onFailure = { e ->
                    _error.value = "Gagal memperbarui profil: ${e.message}"
                    Log.e("TukangHomeVM", "Failed to update profile", e)
                    _loading.value = false
                }
            )
        }
    }
}