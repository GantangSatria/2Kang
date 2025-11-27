package com.gsatria.a2kang.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gsatria.a2kang.core.util.TokenManager
import com.gsatria.a2kang.datasource.RetrofitClient
import com.gsatria.a2kang.datasource.repository.OrderRepository
import com.gsatria.a2kang.model.request.CreateOrderRequest
import com.gsatria.a2kang.model.response.OrderResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val tokenManager = TokenManager(application)
    private val orderRepository = OrderRepository(RetrofitClient.orderApi)

    private val _orderCreated = MutableStateFlow<OrderResponse?>(null)
    val orderCreated: StateFlow<OrderResponse?> = _orderCreated

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun createOrder(
        tukangId: Int,
        scheduledAt: Date,
        address: String,
        paymentMethod: String,
        price: Double,
        notes: String? = null
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

            // Format date to ISO8601 (RFC3339)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val scheduledAtString = dateFormat.format(scheduledAt)

            val request = CreateOrderRequest(
                tukangId = tukangId,
                scheduledAt = scheduledAtString,
                address = address,
                paymentMethod = paymentMethod,
                price = price,
                notes = notes
            )

            val result = orderRepository.createOrder(token, request)

            if (result.isSuccess) {
                _orderCreated.value = result.getOrNull()
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Gagal membuat pesanan"
            }

            _loading.value = false
        }
    }

    fun clearState() {
        _orderCreated.value = null
        _error.value = null
    }
}