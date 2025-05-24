package com.example.tugas2

fun main() {
    val angka = 50
    if (angka % 2 == 0)
        println("Sama rata")
    println("Ini diluar If loh")

    val nilai1 = 3
    val nilai2 = 5.5

    if (nilai1 < nilai2) {
        println("Nilai 3 lebih kecil daripada 5.5")
    } else {
        println("Coba cek lagi...")
    }

    val pernyataan = "Marah"
    if (pernyataan == "Senang") {
        println("Saya juga ikut senang")
    } else if (pernyataan == "Sedih") {
        println("Jangan sedih lagi ya")
    } else {
        println("Saya minta maaf")
    }
    println("Ini contoh saja...")

    val nilai = 10
    if (nilai < 0)
        println("Bilangan Negatif")
    else {
        if (nilai % 2 == 0)
            println("Bilangan Genap")
        else
            println("Bilangan Ganjil")
    }
}