package com.rayhan.tugas511.vPagerFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rayhan.tugas511.R
import com.rayhan.tugas511.databinding.FragmentViewPagerBinding
import com.rayhan.tugas511.vPagerFragment.screens.HalamanKedua
import com.rayhan.tugas511.vPagerFragment.screens.HalamanKetiga
import com.rayhan.tugas511.vPagerFragment.screens.HalamanPertama

class ViewPagerFragment : Fragment() {
    lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager,
            container,  false)
        binding = FragmentViewPagerBinding.bind(view)

        val fragmentList = arrayListOf(
            HalamanPertama(),
            HalamanKedua(),
            HalamanKetiga()
        )

        val adapter = ViewPagerAdapter(fragmentList,
            requireActivity().supportFragmentManager, lifecycle)

        binding.viewPager.adapter = adapter

        return view
    }
}