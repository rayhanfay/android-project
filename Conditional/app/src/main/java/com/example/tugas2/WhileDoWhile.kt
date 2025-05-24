package com.example.tugas2

fun main() {
    var angka = 1

    while (angka > 0) {
        println("Iterasi : $angka")

    }

    var nilai = 0
    do {
        nilai = nilai + 10
        println("Nilai ini berada dalam Do : " + nilai)
    } while (nilai <= 50)
}