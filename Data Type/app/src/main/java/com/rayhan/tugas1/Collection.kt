package com.rayhan.tugas1

fun main() {
    val kataMutable: MutableList<String> = mutableListOf("Hai", "Halo", "Aloha")
    println("List yang menggunakan Mutable" + kataMutable)

    kataMutable.add("Hi")
    kataMutable.removeAt(0)

    println("List mutable setelah di tambahkan " + kataMutable)
    println("List mutable setelah di hapus " + kataMutable)

    repeat(3){
        kataMutable.shuffle()
        println("List mutable steleah shuffle" + kataMutable)
    }

    kataMutable.shuffle()
    println("List mutable setelah shuffle " + kataMutable)

    val kataImmutable: List<String> = kataMutable
    println(kataImmutable)

    println("Kata pertama dari mutable : " + kataImmutable.first())

    val cobaSet: Set<String> = setOf("Belajar", "Pemrograman", "Mobile")
    println("Set : " + cobaSet)

    val cobaMap: Map<Int, String> = mapOf(1 to "Shumaya", 2 to "Resty", 3 to "Ramadhani")
    println("Map : " + cobaMap.values)
}