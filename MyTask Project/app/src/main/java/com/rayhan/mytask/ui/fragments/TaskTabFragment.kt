package com.rayhan.mytask.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rayhan.mytask.TaskMasterApplication
import com.rayhan.mytask.databinding.FragmentTaskTabBinding
import com.rayhan.mytask.ui.adapters.TaskAdapter
import com.rayhan.mytask.ui.detail.TaskDetailActivity
import com.rayhan.mytask.viewmodels.TaskViewModel
import com.rayhan.mytask.viewmodels.TaskViewModelFactory

class TaskTabFragment : Fragment() {

    private var _binding: FragmentTaskTabBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter
    private var tabPosition: Int = 0

    companion object {
        private const val ARG_TAB_POSITION = "tab_position"

        fun newInstance(tabPosition: Int): TaskTabFragment {
            val fragment = TaskTabFragment()
            val args = Bundle()
            args.putInt(ARG_TAB_POSITION, tabPosition)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tabPosition = it.getInt(ARG_TAB_POSITION, 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val taskRepository = (requireActivity().application as TaskMasterApplication).repository
        val factory = TaskViewModelFactory(requireActivity().application, taskRepository)
        taskViewModel = ViewModelProvider(requireActivity(), factory)[TaskViewModel::class.java]

        setupRecyclerView()
        loadTasks()
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            onItemClick = { task ->
                val intent = Intent(requireContext(), TaskDetailActivity::class.java).apply {
                    putExtra("TASK_ID", task.id)
                }
                startActivity(intent)
            },
            onCheckboxClick = { task ->
                taskViewModel.toggleTaskCompletion(task)
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
    }

    private fun loadTasks() {
        when (tabPosition) {
            0 -> loadActiveTasks()
            1 -> loadCompletedTasks()
            2 -> loadAllTasks()
        }
    }

    private fun loadActiveTasks() {
        taskViewModel.activeTasks.observe(viewLifecycleOwner) { tasks ->
            val tasksCopy = ArrayList(tasks)
            taskAdapter.submitList(tasksCopy)
            updateEmptyView(tasksCopy.isEmpty())
        }
    }

    private fun loadCompletedTasks() {
        taskViewModel.completedTasks.observe(viewLifecycleOwner) { tasks ->
            val tasksCopy = ArrayList(tasks)
            taskAdapter.submitList(tasksCopy)
            updateEmptyView(tasksCopy.isEmpty())
        }
    }

    private fun loadAllTasks() {
        taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            val tasksCopy = ArrayList(tasks)
            taskAdapter.submitList(tasksCopy)
            updateEmptyView(tasksCopy.isEmpty())
        }
    }

    private fun updateEmptyView(isEmpty: Boolean) {
        if (isEmpty) {
            binding.emptyView.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.emptyView.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}