package com.rayhan.tugas12

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.rayhan.tugas12.databinding.ActivityItemDetilBinding

class DetilAnggotaAdapter(
    val detilContext: Context,
    val layoutResId: Int,
    val detilList: List<DetilAnggota>
) : ArrayAdapter<DetilAnggota>(detilContext, layoutResId, detilList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(detilContext)
        val binding = ActivityItemDetilBinding.inflate(layoutInflater, parent, false)

        val detil = detilList[position]
        binding.ouAlamat.text = detil.alamat
        binding.ouKodePos.text = detil.kodePos

        return binding.root
    }
}