package com.rayhan.tugas10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.rayhan.tugas10.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    private lateinit var dsManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dsManager = DataStoreManager(applicationContext)

        binding.btnSimpan.setOnClickListener {
            lifecycleScope.launch {
                save(
                    binding.etSimpanNim.text.toString(),
                    binding.etSimpanNama.text.toString()
                )
            }
            Toast.makeText(this@MainActivity, "Data tersimpan", Toast.LENGTH_SHORT).show()
        }

        binding.btnBaca.setOnClickListener {
            lifecycleScope.launch {
                val value = read(binding.etBacaNama.text.toString())
                binding.tvOutput.text = value ?: "Data tidak ditemukan"
            }
        }
    }

    private suspend fun save(nim: String, nama: String) {
        val dataStoreKey = stringPreferencesKey(nim)
        dsManager.dataStore.edit { settings ->
            settings[dataStoreKey] = nama
        }
    }

    private suspend fun read(nim: String): String? {
        val dataStoreKey = stringPreferencesKey(nim)
        val preferences = dsManager.dataStore.data.first()
        return preferences[dataStoreKey]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}