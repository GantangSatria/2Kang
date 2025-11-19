package com.gsatria.a2kang.viewmodel

import androidx.lifecycle.ViewModel
import com.gsatria.a2kang.model.domain.Tukang
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _tukangs = MutableStateFlow<List<Tukang>>(emptyList())
    val tukangs: StateFlow<List<Tukang>> = _tukangs

    init {
        loadTukangs()
    }

    private fun loadTukangs() {
        _tukangs.value = listOf(
            Tukang(
                id = 1,
                name = "Budi Santoso",
                email = "budi@example.com",
                category = listOf("AC").toString(),
                bio = "Teknisi AC berpengalaman 5 tahun",
                services = "Perbaikan dan pemasangan AC",
                rating = 4.9f,
                createdAt = "2024-01-01"
            ),
            Tukang(
                id = 2,
                name = "John Doe",
                email = "john@example.com",
                category = listOf("Konstruksi", "Perbaikan").toString(),
                bio = "Spesialis perbaikan dan konstruksi rumah",
                services = "Renovasi, perbaikan dinding, bangunan kecil",
                rating = 4.6f,
                createdAt = "2024-02-10"
            ),
            Tukang(
                id = 3,
                name = "Ahmad Wijaya",
                email = "ahmad@example.com",
                category = listOf("Listrik").toString(),
                bio = "Ahli listrik rumah dan komersial",
                services = "Instalasi listrik, perbaikan kelistrikan",
                rating = 4.8f,
                createdAt = "2024-03-15"
            ),
            Tukang(
                id = 4,
                name = "Siti Nurhaliza",
                email = "siti@example.com",
                category = listOf("Plumbing", "Air").toString(),
                bio = "Ahli plumbing dan perbaikan pipa bocor",
                services = "Instalasi air, pipa bocor, wastafel",
                rating = 4.7f,
                createdAt = "2024-04-01"
            ),
            Tukang(
                id = 5,
                name = "Bambang Riyanto",
                email = "bambang@example.com",
                category = listOf("Cat").toString(),
                bio = "Tukang cat profesional untuk rumah & kantor",
                services = "Pengecatan tembok interior dan eksterior",
                rating = 4.5f,
                createdAt = "2024-05-20"
            )
        )
    }
}
