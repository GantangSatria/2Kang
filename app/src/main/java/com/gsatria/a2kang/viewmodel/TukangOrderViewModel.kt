package com.gsatria.a2kang.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gsatria.a2kang.core.util.TokenManager
import com.gsatria.a2kang.datasource.RetrofitClient
import com.gsatria.a2kang.datasource.repository.TukangOrderRepository
import com.gsatria.a2kang.model.response.OrderResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TukangOrderViewModel(application: Application) : AndroidViewModel(application) {

    private val tokenManager = TokenManager(application)
    private val repository = TukangOrderRepository(
        RetrofitClient.orderApi,
        RetrofitClient.tukangOrderApi
    )

    private val _orders = MutableStateFlow<List<OrderResponse>>(emptyList())
    val orders: StateFlow<List<OrderResponse>> = _orders

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _actionSuccess = MutableStateFlow<String?>(null)
    val actionSuccess: StateFlow<String?> = _actionSuccess

    fun loadOrders() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val token = tokenManager.getToken()
            if (token == null) {
                _error.value = "Token tidak ditemukan. Silakan login kembali."
                _loading.value = false
                return@launch
            }

            val result = repository.getTukangOrders(token)

            if (result.isSuccess) {
                // Filter hanya pending orders untuk halaman permintaan
                val pendingOrders = result.getOrNull()?.filter { it.status == "pending" } ?: emptyList()
                _orders.value = pendingOrders
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Gagal memuat permintaan"
            }

            _loading.value = false
        }
    }

    fun acceptOrder(orderId: Int) {
        viewModelScope.launch {
            _error.value = null
            _actionSuccess.value = null

            val token = tokenManager.getToken()
            if (token == null) {
                _error.value = "Token tidak ditemukan. Silakan login kembali."
                return@launch
            }

            val result = repository.acceptOrder(token, orderId)

            if (result.isSuccess) {
                _actionSuccess.value = "Pesanan diterima"
                // Reload orders after action
                loadOrders()
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Gagal menerima pesanan"
            }
        }
    }

    fun rejectOrder(orderId: Int) {
        viewModelScope.launch {
            _error.value = null
            _actionSuccess.value = null

            val token = tokenManager.getToken()
            if (token == null) {
                _error.value = "Token tidak ditemukan. Silakan login kembali."
                return@launch
            }

            val result = repository.rejectOrder(token, orderId)

            if (result.isSuccess) {
                _actionSuccess.value = "Pesanan ditolak"
                // Reload orders after action
                loadOrders()
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Gagal menolak pesanan"
            }
        }
    }

    fun startOrder(orderId: Int) {
        viewModelScope.launch {
            _error.value = null
            _actionSuccess.value = null

            val token = tokenManager.getToken()
            if (token == null) {
                _error.value = "Token tidak ditemukan. Silakan login kembali."
                return@launch
            }

            val result = repository.startOrder(token, orderId)

            if (result.isSuccess) {
                _actionSuccess.value = "Pesanan dimulai"
                loadOrders()
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Gagal memulai pesanan"
            }
        }
    }

    fun finishOrder(orderId: Int) {
        viewModelScope.launch {
            _error.value = null
            _actionSuccess.value = null

            val token = tokenManager.getToken()
            if (token == null) {
                _error.value = "Token tidak ditemukan. Silakan login kembali."
                return@launch
            }

            val result = repository.finishOrder(token, orderId)

            if (result.isSuccess) {
                _actionSuccess.value = "Pesanan selesai"
                loadOrders()
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Gagal menyelesaikan pesanan"
            }
        }
    }

    fun clearMessages() {
        _error.value = null
        _actionSuccess.value = null
    }
}