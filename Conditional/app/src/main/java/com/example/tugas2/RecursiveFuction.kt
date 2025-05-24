package com.example.tugas2

fun main() {
    println("Contoh fungsi Rekursive 1")
    fungsiRekursive1()

    println("\nContoh fungsi Rekursive 2")
    print("Masukkan angka faktorial : ")
    val angka: Int = Integer.valueOf(readLine())
    val hasil: Long = faktorial(angka)
    println("Nilai Faktorial dari $angka = $hasil")
}

var hitung = 5
fun fungsiRekursive1() {
    hitung--;
    if (hitung >= 0) {
        println("Halo halo..." + hitung);
        fungsiRekursive1();
    }
}

fun faktorial(n: Int): Long {
    return if (n == 1)
        n.toLong()
    else n * faktorial(  n - 1)
}