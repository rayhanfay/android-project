package com.rayhan.tugas100.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rayhan.tugas100.R
import com.rayhan.tugas100.databinding.FragmentUpdateBinding
import com.rayhan.tugas100.model.Jadwal
import com.rayhan.tugas100.viewmodel.JadwalViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mJadwalViewModel: JadwalViewModel
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root

        mJadwalViewModel = ViewModelProvider(this).get(JadwalViewModel::class.java)

        val waktu = args.curJadwal.waktu.split(":").toTypedArray()
        binding.edtUpdateHari.setText(args.curJadwal.hari)
        binding.edtUpdateJam.setText(waktu[0])
        binding.edtUpdateMenit.setText(waktu[1])
        binding.edtUpdateNamaMk.setText(args.curJadwal.mataKuliah)
        binding.edtUpdateNmDosen.setText(args.curJadwal.namaDosen)

        binding.btnUpdate.setOnClickListener {
            updateItem()
        }

        setHasOptionsMenu(true)
        return view
    }

    private fun updateItem() {
        val hari = binding.edtUpdateHari.text.toString()
        val jam = binding.edtUpdateJam.text.toString()
        val menit = binding.edtUpdateMenit.text.toString()
        val waktu = "$jam:$menit"
        val mk = binding.edtUpdateNamaMk.text.toString()
        val nm_dosen = binding.edtUpdateNmDosen.text.toString()

        if (cekInputan(hari, jam, menit, mk, nm_dosen)) {
            val updatedData = Jadwal(args.curJadwal.id, hari, waktu, mk, nm_dosen)
            mJadwalViewModel.updateJadwal(updatedData)
            Toast.makeText(requireContext(), "Data berhasil ter Update", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Silahkan isi semua datanya", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cekInputan(
        hari: String, jam: String, menit: String, mk: String, dosen: String
    ): Boolean {
        return !(TextUtils.isEmpty(hari) || TextUtils.isEmpty(jam) || TextUtils.isEmpty(menit)
                || TextUtils.isEmpty(mk) || TextUtils.isEmpty(dosen))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_hapus, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_hapus) {
            hapusJadwal()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hapusJadwal() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Iya") { _, _ ->
            mJadwalViewModel.hapusJadwal(args.curJadwal)
            Toast.makeText(
                requireContext(),
                "MK ${args.curJadwal.mataKuliah} berhasil dihapus",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("Tidak") { _, _ -> }
        builder.setTitle("Hapus ${args.curJadwal.mataKuliah} ?")
        builder.setMessage("Apakah kamu yakin ingin menghapus data ini?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
