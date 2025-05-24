package com.example.tugas2

fun main() {
    println("Coba Exception Handling dengan ArrayIndexOutOfBoundsException")

    val angka = arrayOf(1, 2, 3, 4, 5)

    try {
        println("Akses elemen ke-10: ${angka[10]}")
    } catch (e: ArrayIndexOutOfBoundsException) {
        println("Terjadi ArrayIndexOutOfBoundsException: ${e.message}")
    } finally {
        println("Program tetap berjalan setelah menangani exception.")
    }
}