package com.rayhan.tugas100.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.rayhan.tugas100.data.JadwalDb
import com.rayhan.tugas100.repository.JadwalRepo
import com.rayhan.tugas100.model.Jadwal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JadwalViewModel(application: Application) : AndroidViewModel(application) {
    val bacaSemuaData: LiveData<List<Jadwal>>
    private val repo: JadwalRepo

    init {
        val jadwalDAO = JadwalDb.getDb(application).jadwalDao()
        repo = JadwalRepo(jadwalDAO)
        bacaSemuaData = repo.bacaSemuaData
    }

    fun tambahJadwal(jadwal: Jadwal) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.tambahJadwal(jadwal)
        }
    }

    fun updateJadwal(jadwal: Jadwal) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateJadwal(jadwal)
        }
    }

    fun hapusJadwal(jadwal: Jadwal) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.hapusJadwal(jadwal)
        }
    }

    fun hapusSemuaJadwal() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.hapusSemuaJadwal()
        }
    }
}