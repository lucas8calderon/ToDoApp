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
    /**
     * Verifica se há tarefas na lista.
     */
    val hasTasks: Boolean get() = tasks.isNotEmpty()
    
    /**
     * Verifica se há mensagem de erro.
     */
    val hasError: Boolean get() = errorMessage != null
    
    /**
     * Conta o número de tarefas concluídas.
     */
    val completedTasksCount: Int get() = tasks.count { it.isDone }
    
    /**
     * Conta o número de tarefas pendentes.
     */
    val pendingTasksCount: Int get() = tasks.count { !it.isDone }
}
