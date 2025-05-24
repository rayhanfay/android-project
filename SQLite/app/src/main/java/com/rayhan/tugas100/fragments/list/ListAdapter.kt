package com.rayhan.tugas100.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rayhan.tugas100.databinding.CustomRowBinding
import com.rayhan.tugas100.model.Jadwal

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var jadwalList = emptyList<Jadwal>()

    class MyViewHolder(val binding: CustomRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val curItem = jadwalList[position]
        holder.binding.txHari.text = curItem.hari
        holder.binding.txJam.text = curItem.waktu
        holder.binding.txNamaMk.text = curItem.mataKuliah
        holder.binding.txNamaDosen.text = curItem.namaDosen

        holder.binding.cCustomRowLayout.setOnClickListener {
            val aksi = ListFragmentDirections.actionListFragmentToUpdateFragment(curItem)
            it.findNavController().navigate(aksi)
        }
    }

    fun tampilkanData(jadwal: List<Jadwal>) {
        this.jadwalList = jadwal
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return jadwalList.size
    }
}
