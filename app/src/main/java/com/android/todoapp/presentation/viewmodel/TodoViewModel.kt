package com.android.todoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.todoapp.domain.usecase.AddTaskUseCase
import com.android.todoapp.domain.usecase.DeleteTaskUseCase
import com.android.todoapp.domain.usecase.GetTasksUseCase
import com.android.todoapp.domain.usecase.ToggleTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val toggleTaskUseCase: ToggleTaskUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(TodoUiState())
    val uiState: StateFlow<TodoUiState> = _uiState.asStateFlow()
    
    init {
        loadTasks()
    }

    fun onEvent(event: TodoUiEvent) {
        when (event) {
            is TodoUiEvent.AddTask -> addTask(event.title)
            is TodoUiEvent.ToggleTask -> toggleTask(event.task)
            is TodoUiEvent.DeleteTask -> deleteTask(event.task)
            is TodoUiEvent.ClearError -> clearError()
            is TodoUiEvent.LoadTasks -> loadTasks()
        }
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            getTasksUseCase()
                .catch { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Erro ao carregar tarefas: ${exception.message}"
                    )
                }
                .collect { tasks ->
                    _uiState.value = _uiState.value.copy(
                        tasks = tasks,
                        isLoading = false,
                        errorMessage = null
                    )
                }
        }
    }
    
    private fun addTask(title: String) {
        viewModelScope.launch {
            try {
                addTaskUseCase(title)
            } catch (e: IllegalArgumentException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Erro ao adicionar tarefa: ${e.message}"
                )
            }
        }
    }
    
    private fun toggleTask(task: com.android.todoapp.domain.model.Task) {
        viewModelScope.launch {
            try {
                toggleTaskUseCase(task)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Erro ao atualizar tarefa: ${e.message}"
                )
            }
        }
    }
    
    private fun deleteTask(task: com.android.todoapp.domain.model.Task) {
        viewModelScope.launch {
            try {
                deleteTaskUseCase(task)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Erro ao deletar tarefa: ${e.message}"
                )
            }
        }
    }

    private fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}