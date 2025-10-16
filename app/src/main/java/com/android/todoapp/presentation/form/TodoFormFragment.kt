package com.android.todoapp.presentation.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.todoapp.domain.model.Task
import com.android.todoapp.data.repository.TaskRepository
import com.android.todoapp.databinding.FragmentTodoFormBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class TodoFormFragment : Fragment() {

    private var _binding: FragmentTodoFormBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: TaskRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = TaskRepository.getInstance(requireContext())

        binding.btnSave.setOnClickListener {
            val title = binding.etTaskTitle.text.toString()
            if (title.isBlank()) {
                Snackbar.make(binding.root, "Digite um título!", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val task = Task(
                id = 0, // O Room vai gerar o ID automaticamente
                title = title,
                isDone = false
            )

            viewLifecycleOwner.lifecycleScope.launch {
                repository.addTask(task)
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}