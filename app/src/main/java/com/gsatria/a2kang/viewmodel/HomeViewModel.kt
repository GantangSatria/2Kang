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

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val tokenManager = TokenManager(application)
    private val tukangRepository = TukangRepository(RetrofitClient.tukangApi)

    private val _tukangs = MutableStateFlow<List<Tukang>>(emptyList())
    val tukangs: StateFlow<List<Tukang>> = _tukangs

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadTukangs()
    }

    fun loadTukangs(kategori: String? = null) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val token = tokenManager.getToken()
            if (token == null) {
                _error.value = "Token tidak ditemukan. Silakan login kembali."
                _loading.value = false
                return@launch
            }

            val result = tukangRepository.getTukangList(token, kategori)

            if (result.isSuccess) {
                val tukangList = result.getOrNull() ?: emptyList()
                println("DEBUG: Jumlah tukang yang berhasil di-load: ${tukangList.size}")
                _tukangs.value = tukangList
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Gagal memuat data tukang"
                println("DEBUG: Error loading tukang: $errorMsg")
                _error.value = errorMsg
            }

            _loading.value = false
        }
    }

    fun refreshTukangs(kategori: String? = null) {
        loadTukangs(kategori)
    }
}
