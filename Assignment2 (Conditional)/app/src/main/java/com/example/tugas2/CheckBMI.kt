package com.example.tugas2

fun main() {
    hitungBMI( 45.0,  1.65) // berat 45 kg, tinggi 1.65 m
}

fun hitungBMI(berat: Double, tinggi: Double) {
    val bmi = berat / (tinggi * tinggi)
    val kategori = when {
        bmi < 18.5 -> "Kurus"
        bmi < 24.9 -> "Normal"
        bmi < 29.9 -> "Kelebihan Berat Badan"
        else -> "Obesitas"
    }

    println("Berat Badan: $berat kg")
    println("Tinggi Badan: $tinggi m")
    println("BMI Anda: %.2f".format(bmi))
    println("Kategori BMI: $kategori")
}