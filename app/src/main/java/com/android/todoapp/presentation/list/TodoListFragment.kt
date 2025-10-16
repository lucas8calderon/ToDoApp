package com.android.todoapp.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.todoapp.presentation.list.TodoAdapter
import com.android.todoapp.data.repository.TaskRepository
import com.android.todoapp.databinding.FragmentTodoListBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest

class TodoListFragment : Fragment() {

    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: TaskRepository
    private lateinit var adapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = TaskRepository.getInstance(requireContext())
        setupRecyclerView()
        setupFab()
    }

    private fun setupRecyclerView() {
        adapter = TodoAdapter(
            mutableListOf(),
            onToggle = { task ->
                viewLifecycleOwner.lifecycleScope.launch {
                    repository.toggleTask(task)
                }
            },
            onDelete = { task ->
                viewLifecycleOwner.lifecycleScope.launch {
                    repository.removeTask(task)
                }
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repository.getTasks().collectLatest { tasks ->
                adapter.updateData(tasks)
            }
        }
    }

    private fun setupFab() {
        binding.fabAddTask.setOnClickListener {
            findNavController().navigate(com.android.todoapp.R.id.action_list_to_form)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}