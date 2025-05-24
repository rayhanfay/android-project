package com.rayhan.tugas511

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

class SplashScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment first
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Move navigation logic to onViewCreated where the view is fully set up
        Handler(Looper.getMainLooper()).postDelayed({
            // Debug log to check the value
            val isFinished = onBoardingFinished()
            println("DEBUG: onBoardingFinished = $isFinished")

            if (isFinished) {
                findNavController().navigate(
                    R.id.action_splashScreenFragment_to_halamanUtamaFragment)
            } else {
                findNavController().navigate(
                    R.id.action_splashScreenFragment_to_viewPagerFragment)
            }
        }, 3000)
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity()
            .getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        // Make sure we use false as the default value - this is crucial!
        return sharedPref.getBoolean("Selesai", false)
    }
}