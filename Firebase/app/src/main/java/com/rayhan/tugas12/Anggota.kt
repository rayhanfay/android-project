package com.rayhan.tugas12

data class Anggota(
    val id: String,
    val nama: String,
    val kota: String,
    val usia: String,
    val hp: String,
) {
    constructor() : this( "",  "",  "", "", "")
}
