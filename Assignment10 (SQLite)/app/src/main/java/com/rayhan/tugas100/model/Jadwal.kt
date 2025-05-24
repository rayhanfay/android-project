package com.rayhan.tugas100.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "jadwal_kuliah")
data class Jadwal (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val hari: String,
    val waktu: String,
    val mataKuliah: String,
    val namaDosen: String
) : Parcelable