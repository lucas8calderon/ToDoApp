package com.android.todoapp.ui.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.todoapp.data.model.Task
import com.android.todoapp.data.model.repository.TaskRepository
import com.android.todoapp.databinding.FragmentTodoFormBinding
import com.google.android.material.snackbar.Snackbar

class TodoFormFragment : Fragment() {

    private var _binding: FragmentTodoFormBinding? = null
    private val binding get() = _binding!!
    private val repository = TaskRepository.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            val title = binding.etTaskTitle.text.toString()
            if (title.isBlank()) {
                Snackbar.make(binding.root, "Digite um título!", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val task = Task(
                id = System.currentTimeMillis().toInt(),
                title = title,
                isDone = false
            )
            repository.addTask(task)
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}