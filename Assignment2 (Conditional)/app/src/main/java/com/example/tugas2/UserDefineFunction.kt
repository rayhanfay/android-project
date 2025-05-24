package com.example.tugas2

fun main() {
    namaKu()
    contohReturn( "Rayhan", 20)
    println("Ini hasil dari function jumlah : ${jumlah( 5, 10, 15, 20)}")
}

fun namaKu() {
    println("Mugiwara Sambul")
}

fun contohReturn(nama: String, umur: Int) {
    println("Halo, namaku $nama. Umurku $umur tahun")
}

fun jumlah(vararg angka: Int): Int {
    var hasilJumlah = 0
    angka.forEach { nilai -> hasilJumlah += nilai }

    return hasilJumlah
}