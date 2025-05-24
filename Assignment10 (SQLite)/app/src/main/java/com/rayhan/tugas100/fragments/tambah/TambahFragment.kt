package com.rayhan.tugas100.fragments.tambah

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rayhan.tugas100.R
import com.rayhan.tugas100.model.Jadwal
import com.rayhan.tugas100.viewmodel.JadwalViewModel
import com.rayhan.tugas100.databinding.FragmentTambahBinding

class TambahFragment : Fragment() {

    private lateinit var mJadwalViewModel: JadwalViewModel
    private var _binding: FragmentTambahBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTambahBinding.inflate(inflater, container, false)
        val view = binding.root

        mJadwalViewModel = ViewModelProvider(this).get(JadwalViewModel::class.java)

        binding.btnAdd.setOnClickListener {
            masukkanDataKeDatabase()
        }

        return view
    }

    private fun masukkanDataKeDatabase() {
        val hari = binding.edtHari.text.toString()
        val jam = binding.edtJam.text.toString()
        val menit = binding.edtMenit.text.toString()
        val mk = binding.edtNamaMk.text.toString()
        val dosen = binding.edtNmDosen.text.toString()
        val waktu = "$jam:$menit"
        val nmDosen = "d. $dosen"

        if (cekInput(hari, jam, menit, mk, dosen)) {
            val jadwal = Jadwal(0, hari, waktu, mk, nmDosen)
            mJadwalViewModel.tambahJadwal(jadwal)
            Toast.makeText(requireContext(), "Okeh berhasil ditambahkan", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_tambahFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Silahkan isi dulu fieldnya", Toast.LENGTH_LONG).show()
        }
    }

    private fun cekInput(
        hari: String,
        jam: String,
        menit: String,
        mk: String,
        dosen: String
    ): Boolean {
        return !(TextUtils.isEmpty(hari) || TextUtils.isEmpty(jam) || TextUtils.isEmpty(menit) ||
                TextUtils.isEmpty(mk) || TextUtils.isEmpty(dosen))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}