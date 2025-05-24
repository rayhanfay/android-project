package com.rayhan.tugas6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.rayhan.tugas6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val mataPelajaran = arrayOf(
        "Matematika",
        "Pemrograman Mobile",
        "Agama",
        "Bahasa Inggris",
        "Jaringan Dasar",
        "Pengembangan Game",
        "Animasi",
        "Pemrograman Web",
        "Konsep Teknologi Informasi",
        "Sistem Operasi",
        "Aljabar Matriks",
        "Pengembangan Game",
        "Animasi",
        "Pemrograman Web",
        "Konsep Teknologi Informasi",
        "Sistem Operasi",
        "Aljabar Matriks"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val arrayAdapter = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, mataPelajaran)
        binding.listview.adapter = arrayAdapter

        binding.listview.setOnItemClickListener { parent: AdapterView<*>, view: View,
                                                  posisi: Int, id: Long ->
            Toast.makeText(
                this, "Klik: " + (posisi+1) + " " +
                        mataPelajaran[posisi], Toast.LENGTH_SHORT
            ).show()
        }
    }
}