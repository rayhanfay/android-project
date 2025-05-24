package com.example.tugas2

fun main() {
    for (nilai: Int in 15 .. 25) {
        print(" $nilai")
    }
    println()

    val kampusKu: Array<String> = arrayOf("Kampus", "Universitas", "Riau")
    for (kampus: String in kampusKu) {
        println(kampus)
    }

    for (n: Int in kampusKu.indices) {
        println("Isi array [$n] = ${kampusKu[n]}")
    }

    val cobaArray: Array<Int> = arrayOf(2, 4, 5, 8, 11)
    for ((idx: Int, nilai: Int) in cobaArray.withIndex()) {
        println("Isi pada index $idx adalah : $nilai")
        if (nilai % 2 == 0) {
            println("Bilangan Genap : $nilai")
        } else {
            println("Bilangan Ganjil : $nilai")
        }
    }
}