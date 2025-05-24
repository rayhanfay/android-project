package com.example.tugas2


fun main() {
    for (n: Int in 1 .. 10) {
        println("Sebelum break, Nilai : $n")
        if (n == 5) {
            println("Proses Loop berhenti karena break")
            break
        }
    }

    for (ch: Char in 'A' .. 'C') {
        for (n: Int in 1 .. 4) {
            println("$ch and $n")
            if (n == 2) break
        }
    }

    println()
    contohLoop@ for (nilai: Int in 1 .. 10) {
        if (nilai == 5) {
            println("Nilai ini berada pada block If " + nilai + "\n-- Jadi program akan berhenti")
            break@contohLoop
        } else {
            println("Nilai ini berada pada block Else " + nilai)
            continue@contohLoop
        }
    }
}