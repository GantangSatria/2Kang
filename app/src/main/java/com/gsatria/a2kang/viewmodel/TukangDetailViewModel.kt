package com.gsatria.a2kang.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gsatria.a2kang.core.util.TokenManager
import com.gsatria.a2kang.datasource.RetrofitClient
import com.gsatria.a2kang.datasource.repository.TukangRepository
import com.gsatria.a2kang.model.domain.Tukang
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TukangDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val tokenManager = TokenManager(application)
    private val tukangRepository = TukangRepository(RetrofitClient.tukangApi)

    private val _tukangDetail = MutableStateFlow<Tukang?>(null)
    val tukangDetail: StateFlow<Tukang?> = _tukangDetail

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadTukangDetail(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val token = tokenManager.getToken()
            if (token == null) {
                _error.value = "Token tidak ditemukan. Silakan login kembali."
                _loading.value = false
                return@launch
            }

            val result = tukangRepository.getTukangDetail(token, id)

            if (result.isSuccess) {
                _tukangDetail.value = result.getOrNull()
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Gagal memuat detail tukang"
            }

            _loading.value = false
        }
    }
}
