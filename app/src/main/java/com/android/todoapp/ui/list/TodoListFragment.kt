package com.android.todoapp.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.todoapp.data.model.TodoAdapter
import com.android.todoapp.data.model.repository.TaskRepository
import com.android.todoapp.databinding.FragmentTodoListBinding

class TodoListFragment : Fragment() {

    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!

    private val repository = TaskRepository.getInstance()
    private lateinit var adapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupFab()
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    private fun setupRecyclerView() {
        adapter = TodoAdapter(
            repository.getTasks().toMutableList(),
            onToggle = { task ->
                repository.toggleTask(task)
                refreshList()
            },
            onDelete = { task ->
                repository.removeTask(task)
                refreshList()
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    private fun setupFab() {
        binding.fabAddTask.setOnClickListener {
            findNavController().navigate(com.android.todoapp.R.id.action_list_to_form)
        }
    }

    private fun refreshList() {
        binding.recyclerView.post {
            adapter.updateData(repository.getTasks())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}