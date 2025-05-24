package com.example.tugas2

fun main() {
    for (n: Int in 20 .. 30) {
        if (n % 2 != 0)
            continue
        println("$n")
    }

    for (n: Int in 5 .. 10) {
        println("Sebelum Continue, Nilai : $n")
        if (n == 6 || n == 8)
            continue
        println("Sesudah Continue, Nilai : $n")
    }

    for (x: Char in 'A' .. 'D') {
        for (n: Int in 1 .. 5) {
            if (n == 2 || n == 4)
                continue
            println("$x and $n")
        }
    }
}