package com.rayhan.tugas511

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.rayhan.tugas511.databinding.FragmentHalamanUtamaBinding

class HalamanUtamaFragment : Fragment() {
    private lateinit var binding: FragmentHalamanUtamaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_halaman_utama,
            container,  false)
        binding = FragmentHalamanUtamaBinding.bind(view)

        // Remove this line as it's likely causing a crash
        // val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.txtUtama.setOnClickListener {
            Toast.makeText(context, "Ini Halaman Home", Toast.LENGTH_SHORT).show()
        }
        return view
    }
}