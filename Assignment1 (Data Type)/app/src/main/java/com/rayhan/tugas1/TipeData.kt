package com.rayhan.tugas1

fun main() {
    println("Hai teman-teman Teknik Informatika")
    println("Selamat sudah berhasil naik kelas")

    var angka = 15
    println("Hasil dari 15 +10 = ${angka + 10}")

    val nilaiInt = 10000
    val nilaiDouble = 100.003
    val nilaiFloat = 100.00f
    val nilaiLong: Long = 100000000000004
    val nilaiShort: Short = 10
    val nilaiByte: Byte = 1

    println("Nilai Integer " + nilaiInt)
    println("Nilai Double " + nilaiDouble)
    println("Nilai Float " + nilaiFloat)
    println("Nilai Long " + nilaiLong)
    println("Nilai Short " + nilaiShort)
    println("Nilai Byte " + nilaiByte)

    val huruf = 'a'
    println("Ini penggunaan karakter:${huruf}")

    val nilaiBoolean = true
    println("Nilai Boolean yang dibuat adalah $nilaiBoolean")

    val nilaiString = "Rayhan Al Farassy"
    println("Halo " + nilaiString + "!\nApa kabar?")
}