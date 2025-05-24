package com.rayhan.tugas100.repository

import androidx.lifecycle.LiveData
import com.rayhan.tugas100.data.JadwalDAO
import com.rayhan.tugas100.model.Jadwal

class JadwalRepo(private val jadwalDAO: JadwalDAO) {

    val bacaSemuaData: LiveData<List<Jadwal>> = jadwalDAO.bacaSemuaData()

    suspend fun tambahJadwal(jadwal: Jadwal) {
        jadwalDAO.tambahJadwal(jadwal)
    }

    suspend fun updateJadwal(jadwal: Jadwal) {
        jadwalDAO.updateJadwal(jadwal)
    }

    suspend fun hapusJadwal(jadwal: Jadwal) {
        jadwalDAO.hapusJadwal(jadwal)
    }

    suspend fun hapusSemuaJadwal() {
        jadwalDAO.hapusSemua()
    }
}