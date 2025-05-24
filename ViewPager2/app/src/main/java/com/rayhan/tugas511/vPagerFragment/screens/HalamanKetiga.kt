package com.rayhan.tugas511.vPagerFragment.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rayhan.tugas511.R
import com.rayhan.tugas511.databinding.FragmentHalamanKetigaBinding

class HalamanKetiga : Fragment() {
    private lateinit var binding: FragmentHalamanKetigaBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(
            R.layout.fragment_halaman_ketiga,
            container,
            false
        )
        binding = FragmentHalamanKetigaBinding.bind(view)

        binding.txtSelesai.setOnClickListener {
            // First mark onboarding as finished
            onBoardingFinished()
            // Then navigate
            findNavController().navigate(
                R.id.action_viewPagerFragment_to_halamanUtamaFragment)
        }
        return view
    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity()
            .getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Selesai", true)
        // Make sure to commit these changes immediately
        editor.commit() // Using commit instead of apply for immediate effect
        println("DEBUG: Setting onBoarding finished to true")
    }
}