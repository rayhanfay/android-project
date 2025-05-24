package com.rayhan.tugas61

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rayhan.tugas61.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var dosenAdapter: DosenRecyclerAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        tambahDataSet()
    }

    private fun tambahDataSet() {
        val data = SumberData.buatSetData()
        dosenAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager( this@MainActivity)
            val spacingAtas = DekorasiSpasiGambar( 20)
            addItemDecoration(spacingAtas)
            dosenAdapter = DosenRecyclerAdapter()
            adapter = dosenAdapter
        }
    }
}