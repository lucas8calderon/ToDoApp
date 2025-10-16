package com.android.todoapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.todoapp.data.repository.TaskRepository
import com.android.todoapp.domain.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TaskRepository.Companion.getInstance(application)

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _tasksLiveData = MutableLiveData<List<Task>>()
    val tasksLiveData: LiveData<List<Task>> = _tasksLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.getTasks().collect { taskList ->
                    _tasks.value = taskList
                    _tasksLiveData.value = taskList
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao carregar tarefas: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addTask(title: String) {
        if (title.isBlank()) {
            _errorMessage.value = "Digite um título para a tarefa!"
            return
        }

        val task = Task(
            id = 0,
            title = title.trim(),
            isDone = false
        )

        viewModelScope.launch {
            try {
                repository.addTask(task)
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao adicionar tarefa: ${e.message}"
            }
        }
    }

    fun toggleTask(task: Task) {
        viewModelScope.launch {
            try {
                repository.toggleTask(task)
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao atualizar tarefa: ${e.message}"
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                repository.removeTask(task)
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao deletar tarefa: ${e.message}"
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}