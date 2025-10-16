package com.android.todoapp.presentation.viewmodel

import com.android.todoapp.domain.model.Task

/**
 * Representa eventos da UI para a tela de tarefas.
 * Segue o princípio de responsabilidade única (SRP).
 */
sealed class TodoUiEvent {
    data class AddTask(val title: String) : TodoUiEvent()
    data class ToggleTask(val task: Task) : TodoUiEvent()
    data class DeleteTask(val task: Task) : TodoUiEvent()
    object ClearError : TodoUiEvent()
    object LoadTasks : TodoUiEvent()
}
