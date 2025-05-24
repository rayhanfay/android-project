package com.example.tugas2

fun main() {
    val r = 7.0
    val t = 15.0
    val s = 10.0

    println("Luas Permukaan Tabung: ${luasTabung(r, t)} cm^2")
    println("Volume Tabung: ${volumeTabung(r, t)} cm^3")
    println("Luas Permukaan Kubus: ${luasKubus(s)} cm^2")
    println("Volume Kubus: ${volumeKubus(s)} cm^3")
    println("Luas Permukaan Bola: ${luasBola(r)} cm^2")
    println("Volume Bola: ${volumeBola(r)} cm^3")
}

inline fun luasTabung(r: Double, t: Double) = 2 * Math.PI * r * (r + t)
inline fun volumeTabung(r: Double, t: Double) = Math.PI * r * r * t
inline fun luasKubus(s: Double) = 6 * s * s
inline fun volumeKubus(s: Double) = s * s * s
inline fun luasBola(r: Double) = 4 * Math.PI * r * r
inline fun volumeBola(r: Double) = (4.0 / 3.0) * Math.PI * r * r * r