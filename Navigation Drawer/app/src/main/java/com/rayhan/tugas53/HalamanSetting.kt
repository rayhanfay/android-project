package com.rayhan.tugas53

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 * Use the [HalamanSetting.newInstance] factory method to
 * create an instance of this fragment.
 */
class HalamanSetting : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_halaman_setting,
            container,  false)
    }
}