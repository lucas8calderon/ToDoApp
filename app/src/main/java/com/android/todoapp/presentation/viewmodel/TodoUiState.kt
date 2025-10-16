package com.android.todoapp.presentation.viewmodel

import com.android.todoapp.domain.model.Task

/**
 * Representa o estado da UI para a tela de tarefas.
 * Segue o princípio de responsabilidade única (SRP).
 */
data class TodoUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val hasTasks: Boolean get() = tasks.isNotEmpty()
    val hasError: Boolean get() = errorMessage != null
    val completedTasksCount: Int get() = tasks.count { it.isDone }
    val pendingTasksCount: Int get() = tasks.count { !it.isDone }
}
