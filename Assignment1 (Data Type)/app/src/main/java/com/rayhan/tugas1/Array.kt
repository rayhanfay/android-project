package com.rayhan.tugas1

fun main() {
    val cobaArray: Array<String> = arrayOf("Silahkan", "Tuliskan", "Apa", "Saja")
    println("Data cobaArray Index 0: [${cobaArray[0]}]")
    println("Data cobaArray Index 3: [${cobaArray[3]}]")
    println("Isi seluruh cobaArray: ${cobaArray.contentDeepToString()}")
    println("Panjang cobaArray: " + cobaArray.size)
    println("Get cobaArray Index 2: ${cobaArray.get(2)}")

    val cobaArray2: Array<Int> = arrayOf(1, 2, 3, 4, 5)
    println("Data cobaArray2 Index 2: [${cobaArray2[2]}]")
    println("Isi seluruh cobaArray: ${cobaArray2.contentDeepToString()}")
    println("Panjang cobaArray : " + cobaArray.size)

    val cobaArray3 = cobaArray + cobaArray2.map{it.toString()}.toTypedArray()
    println("Isi seluruh cobaArray3 : ${cobaArray3.contentDeepToString()}")

    cobaArray2.set(4, 10)
    println("Hasil cobaArray2 Index 4 setelah di Set : ${cobaArray2[4]}")
}