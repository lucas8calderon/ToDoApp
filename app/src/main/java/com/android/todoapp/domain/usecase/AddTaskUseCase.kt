package com.android.todoapp.domain.usecase

import com.android.todoapp.domain.model.Task
import com.android.todoapp.domain.repository.TaskRepository
import javax.inject.Inject

/**
 * Use Case para adicionar uma nova tarefa.
 * Encapsula a lógica de negócio para criação de tarefas.
 */
class AddTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(title: String) {
        if (title.isBlank()) {
            throw IllegalArgumentException("Título da tarefa não pode estar vazio")
        }
        
        val task = Task(
            id = 0,
            title = title.trim(),
            isDone = false
        )
        
        repository.addTask(task)
    }
}