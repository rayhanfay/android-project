
package com.rayhan.tugas12

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.database.*
import com.rayhan.tugas12.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var ref: DatabaseReference
    private lateinit var anggotaList: MutableList<Anggota>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fix the Firebase region issue by specifying the correct database URL
        ref = FirebaseDatabase.getInstance("https://rayandro-tugas12-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("anggota")

        binding.btnSimpan.setOnClickListener(this)
        anggotaList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    anggotaList.clear()
                    for (a in snapshot.children) {
                        val anggota = a.getValue(Anggota::class.java)
                        if (anggota != null) {
                            anggotaList.add(anggota)
                        }
                    }

                    val adapter = AnggotaAdapter(
                        this@MainActivity,
                        R.layout.activity_item_anggota,
                        anggotaList
                    )
                    binding.lvHasil.adapter = adapter

                    println("Output = $anggotaList")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Database error: ${error.message}",
                    Toast.LENGTH_SHORT).show()
            }
        })

        binding.lvHasil.setOnItemClickListener { _, _, position, _ ->
            val anggota = anggotaList[position]
            val intent = Intent(this@MainActivity, TambahAnggota::class.java)
            intent.putExtra(TambahAnggota.EXTRA_ID, anggota.id)
            intent.putExtra(TambahAnggota.EXTRA_NAMA, anggota.nama)
            startActivity(intent)
        }
    }

    override fun onClick(v: View?) {
        simpanData()
    }

    private fun simpanData() {
        val nama = binding.edtNama.text.toString().trim()
        val hp = binding.edtNoHp.text.toString().trim()
        val usia = binding.edtUsia.text.toString().trim()
        val kota = binding.edtKota.text.toString().trim()

        // Validasi form
        if (nama.isEmpty() || hp.isEmpty() || usia.isEmpty() || kota.isEmpty()) {
            Toast.makeText(this, "Mohon data jangan dibiarkan tidak boleh kosong",
                Toast.LENGTH_SHORT).show()
            return
        }

        val anggotaId = ref.push().key

        if (anggotaId != null) {
            val anggota = Anggota(anggotaId, nama, kota, usia, hp)

            ref.child(anggotaId).setValue(anggota).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Data berhasil ditambahkan",
                        Toast.LENGTH_SHORT).show()

                    // Clear input fields after successful save
                    binding.edtNama.text.clear()
                    binding.edtNoHp.text.clear()
                    binding.edtUsia.text.clear()
                    binding.edtKota.text.clear()
                } else {
                    Toast.makeText(applicationContext, "Gagal menyimpan data: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(applicationContext, "Gagal membuat ID untuk data baru",
                Toast.LENGTH_SHORT).show()
        }
    }
}