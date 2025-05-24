package com.rayhan.tugas100.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rayhan.tugas100.R
import com.rayhan.tugas100.databinding.FragmentListBinding
import com.rayhan.tugas100.viewmodel.JadwalViewModel

class ListFragment : Fragment() {

    private lateinit var mJadwalViewModel: JadwalViewModel
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        val adapter = ListAdapter()
        val rv = binding.recyclerview
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        mJadwalViewModel = ViewModelProvider(this).get(JadwalViewModel::class.java)
        mJadwalViewModel.bacaSemuaData.observe(viewLifecycleOwner, Observer { jadwal ->
            adapter.tampilkanData(jadwal)
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_tambahFragment)
        }

        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_hapus, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_hapus) {
            hapusSemua()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hapusSemua() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Iya") { _, _ ->
            mJadwalViewModel.hapusSemuaJadwal()
            Toast.makeText(requireContext(), "Semua data berhasil dihapus", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Tidak") { _, _ -> }
        builder.setTitle("Hapus semua?")
        builder.setMessage("Apakah kamu yakin ingin menghapus seluruh data ini?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
