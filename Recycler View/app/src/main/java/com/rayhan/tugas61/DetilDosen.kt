package com.rayhan.tugas61

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rayhan.tugas61.databinding.DetilDosenBinding

class DetilDosen : AppCompatActivity() {
    private lateinit var binding: DetilDosenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetilDosenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foto = intent.getStringExtra("fotonya") ?: ""
        val nama = intent.getStringExtra("namanya") ?: ""
        val kompetensi = intent.getStringExtra("kompetensinya") ?: ""
        val nip = intent.getStringExtra("nipnya") ?: ""
        val mataKuliah = intent.getStringExtra("mataKuliahnya") ?: ""
        val ruangan = intent.getStringExtra("ruangannya") ?: ""

        setDetil(foto, nama, kompetensi, nip, mataKuliah, ruangan)
    }

    private fun setDetil(foto: String, nama: String, kompetensi: String, nip: String, mataKuliah: String, ruangan: String) {
        val requestOp = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        binding.namaDetilDosen.text = nama
        binding.nipDetilDosen.text = "NIP: $nip"
        binding.kompetensiDetilDosen.text = "Kompetensi: $kompetensi"
        binding.mkDetilDosen.text = "Mata Kuliah: $mataKuliah"
        binding.ruanganDetilDosen.text = "Ruangan: $ruangan"

        Glide.with(this)
            .load(foto)
            .apply(requestOp)
            .centerCrop()
            .into(binding.fotoDetil)
    }
}