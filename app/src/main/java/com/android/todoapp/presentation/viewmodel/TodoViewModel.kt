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

/**
 * ViewModel responsável por gerenciar o estado da UI de tarefas.
 * Segue o padrão MVVM e Clean Architecture.
 * 
 * Princípios SOLID aplicados:
 * - SRP: Responsável apenas por gerenciar estado da UI
 * - DIP: Depende de abstrações (Use Cases) não de implementações
 * - OCP: Aberto para extensão, fechado para modificação
 */
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
    
    /**
     * Processa eventos da UI.
     * Segue o princípio de responsabilidade única (SRP).
     */
    fun onEvent(event: TodoUiEvent) {
        when (event) {
            is TodoUiEvent.AddTask -> addTask(event.title)
            is TodoUiEvent.ToggleTask -> toggleTask(event.task)
            is TodoUiEvent.DeleteTask -> deleteTask(event.task)
            is TodoUiEvent.ClearError -> clearError()
            is TodoUiEvent.LoadTasks -> loadTasks()
        }
    }
    
    /**
     * Carrega todas as tarefas usando o Use Case.
     * Segue o princípio de responsabilidade única (SRP).
     */
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
    
    /**
     * Adiciona uma nova tarefa usando o Use Case.
     * @param title Título da tarefa
     */
    private fun addTask(title: String) {
        viewModelScope.launch {
            try {
                addTaskUseCase(title)
                // A lista será atualizada automaticamente via Flow
            } catch (e: IllegalArgumentException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Erro ao adicionar tarefa: ${e.message}"
                )
            }
        }
    }
    
    /**
     * Alterna o status de conclusão de uma tarefa usando o Use Case.
     * @param task Tarefa a ser atualizada
     */
    private fun toggleTask(task: com.android.todoapp.domain.model.Task) {
        viewModelScope.launch {
            try {
                toggleTaskUseCase(task)
                // A lista será atualizada automaticamente via Flow
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Erro ao atualizar tarefa: ${e.message}"
                )
            }
        }
    }
    
    /**
     * Deleta uma tarefa usando o Use Case.
     * @param task Tarefa a ser deletada
     */
    private fun deleteTask(task: com.android.todoapp.domain.model.Task) {
        viewModelScope.launch {
            try {
                deleteTaskUseCase(task)
                // A lista será atualizada automaticamente via Flow
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Erro ao deletar tarefa: ${e.message}"
                )
            }
        }
    }
    
    /**
     * Limpa mensagens de erro da UI.
     */
    private fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
