package com.rayhan.tugas3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rayhan.tugas3.databinding.ActivityLayoutCalculatorBinding

class KalkulatorLayout : AppCompatActivity() {
    private lateinit var binding: ActivityLayoutCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLayoutCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnHapus.setOnClickListener {
            binding.edtPanjang.setText("")
            binding.edtLebar.setText("")
            binding.edtTinggi.setText("")
        }

        binding.btnHitung.setOnClickListener {
            val panjang = Integer.valueOf(binding.edtPanjang.getText().toString())
            val lebar = Integer.valueOf(binding.edtLebar.getText().toString())
            val tinggi = Integer.valueOf(binding.edtTinggi.getText().toString())
            val luas = 2 * (panjang * lebar + panjang * tinggi + lebar * tinggi)
            val keliling = 4 * (panjang + lebar + tinggi)
            val volume = panjang * lebar * tinggi
            val teksOutput = "Hasil Perhitungan \n\nLuas = $luas cm² " +
                    "\nKeliling = $keliling cm \nVolume = $volume cm³"
            binding.txHasil.setText(teksOutput)
            binding.txHasil.setTextColor(getColor(R.color.colorAccent))
        }

        binding.btnHapus.setOnClickListener {
            binding.edtPanjang.setText("")
            binding.edtLebar.setText("")
            binding.edtTinggi.setText("")
            binding.txHasil.setText("Hasil Perhitungan") // Reset teks output ke nilai default
            binding.txHasil.setTextColor(getColor(android.R.color.black)) // Reset warna teks ke default
        }
    }
}