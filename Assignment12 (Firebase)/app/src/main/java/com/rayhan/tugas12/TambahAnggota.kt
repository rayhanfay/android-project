package com.rayhan.tugas12

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.rayhan.tugas12.databinding.ActivityTambahDetilBinding

class TambahAnggota : AppCompatActivity() {
    private lateinit var detilList: MutableList<DetilAnggota>
    private lateinit var ref: DatabaseReference
    private lateinit var binding: ActivityTambahDetilBinding

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAMA = "extra_nama"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDetilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(EXTRA_ID)
        val nama = intent.getStringExtra(EXTRA_NAMA)

        // Display the anggota name in a TextView if you have one
        // binding.tvNamaAnggota.text = nama ?: "Detil Anggota"

        detilList = mutableListOf()

        // Fix the Firebase instance to use the correct URL
        ref = FirebaseDatabase.getInstance("https://rayandro-tugas12-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("detil anggota")
            .child(id!!)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    detilList.clear()
                    for (s in snapshot.children) {
                        val detil = s.getValue(DetilAnggota::class.java)
                        if (detil != null) {
                            detilList.add(detil)
                        }
                    }

                    val adapter = DetilAnggotaAdapter(
                        this@TambahAnggota,
                        R.layout.activity_item_detil,
                        detilList
                    )
                    binding.lvTambahDetil.adapter = adapter

                    println("Output = " + detilList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Implement proper error handling
                Toast.makeText(this@TambahAnggota, "Database error: ${error.message}",
                    Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnTambahDetil.setOnClickListener { simpanDetil() }
    }

    private fun simpanDetil() {
        val alamat = binding.edtAlamat.text.toString().trim()
        val kodePos = binding.edtKodePos.text.toString()

        if (alamat.isEmpty()) {
            binding.edtAlamat.error = "Isi alamat terlebih dahulu"
            return
        }

        if (kodePos.isEmpty()) {
            binding.edtKodePos.error = "Isi kode pos terlebih dahulu"
            return
        }

        val detilId = ref.push().key

        if (detilId != null) {
            val detil = DetilAnggota(detilId, alamat, kodePos)

            ref.child(detilId).setValue(detil).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Informasi tambahan berhasil ditambahkan",
                        Toast.LENGTH_SHORT).show()

                    // Clear input fields after successful save
                    binding.edtAlamat.text.clear()
                    binding.edtKodePos.text.clear()
                } else {
                    Toast.makeText(applicationContext, "Gagal menyimpan: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(applicationContext, "Gagal membuat ID untuk data baru",
                Toast.LENGTH_SHORT).show()
        }
    }
}