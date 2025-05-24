package com.rayhan.tugas12

data class DetilAnggota(
    val id: String,
    val alamat: String,
    val kodePos: String
) {
    constructor() : this( "",  "",  "")
}