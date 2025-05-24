package com.example.tugas2

fun main() {


    val huruf = 'E'
    when (huruf) {
        'A' -> println("A = huruf vokal")
        'E' -> println("E = huruf vokal")
        'I' -> println("I = huruf vokal")
        'O' -> println("O = huruf vokal")
        'U' -> println("U = huruf vokal")
        else -> println("Ini huruf konsonan")
    }

    val angka = 11
    when (angka) {
        2, 4, 6, 8, 10 -> println("Angka Genap")
        else -> println("Angka Ganjil")
    }

    val nilai = 88
    when (nilai) {
        in 1 .. 9 -> println("$nilai berada diantara angka 1-9")
        in 10 .. 99 -> println("$nilai berada diantara angka 10-99")
        in 100 .. 999 -> println("$nilai berada diantara angka 100-999")
        else -> println("$nilai merupakan bilangan ribuan")
    }

    val nilaiKuis = 40
    when (nilaiKuis) {
        in 0 .. 50 -> {
            val nilaiLulus: Int = 51 - nilaiKuis
            println(
                "Kamu ikut revisi ya. Untuk lulus, nilai kamu harus bertambah " +
                        "$nilaiLulus poin lagi."
            )
        }

        in 51 .. 100 -> println("Selamat kamu lulus ujian.")
    }
}