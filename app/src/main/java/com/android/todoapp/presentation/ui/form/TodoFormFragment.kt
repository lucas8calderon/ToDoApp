package com.android.todoapp.presentation.ui.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.todoapp.databinding.FragmentTodoFormBinding
import com.android.todoapp.presentation.viewmodel.TodoViewModel
import com.android.todoapp.presentation.viewmodel.TodoUiEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TodoFormFragment : Fragment() {

    private var _binding: FragmentTodoFormBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupClickListeners()
        observeViewModel()
    }
    
    private fun setupClickListeners() {
        binding.btnSave.setOnClickListener {
            val title = binding.etTaskTitle.text.toString()
            viewModel.onEvent(TodoUiEvent.AddTask(title))
        }
    }
    
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                uiState.errorMessage?.let { errorMessage ->
                    Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
                    viewModel.onEvent(TodoUiEvent.ClearError)
                }

                if (uiState.tasks.isNotEmpty() && binding.etTaskTitle.text.toString().isNotBlank()) {
                    binding.etTaskTitle.text?.clear()
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}