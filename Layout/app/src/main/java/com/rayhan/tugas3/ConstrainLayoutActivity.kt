package com.rayhan.tugas3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ConstrainLayoutActivity : AppCompatActivity() {

    private lateinit var edtHobi: EditText
    private lateinit var edtFav: EditText
    private lateinit var btnSimpan: Button
    private lateinit var btnHapus: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constrain_layout)

        // Initialize views
        edtHobi = findViewById(R.id.edt_cl_hobi)
        edtFav = findViewById(R.id.edt_cl_fav)
        btnSimpan = findViewById(R.id.btn_simpan)
        btnHapus = findViewById(R.id.btn_hapus)

        // Set click listener for save button
        btnSimpan.setOnClickListener {
            val hobi = edtHobi.text.toString()
            val favorit = edtFav.text.toString()

            // Show Toast with hobby and favorite information
            val message = "Hobi: $hobi, Favorit: $favorit"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        // Set click listener for clear button
        btnHapus.setOnClickListener {
            // Clear text fields
            edtHobi.setText("")
            edtFav.setText("")
        }
    }
}