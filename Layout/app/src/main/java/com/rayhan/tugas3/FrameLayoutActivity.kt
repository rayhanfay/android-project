package com.rayhan.tugas3

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rayhan.tugas3.databinding.ActivityFrameLayoutBinding


class FrameLayoutActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFrameLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrameLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onClick(v: View?) {
        binding.btnKlik.visibility = View.GONE
        binding.txtTombol.visibility = View.VISIBLE
    }
}