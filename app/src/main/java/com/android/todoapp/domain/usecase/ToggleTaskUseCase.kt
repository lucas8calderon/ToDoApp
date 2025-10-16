package com.android.todoapp.domain.usecase

import com.android.todoapp.domain.model.Task
import com.android.todoapp.domain.repository.TaskRepository
import javax.inject.Inject

/**
 * Use Case para alternar o status de conclusão de uma tarefa.
 * Encapsula a lógica de negócio para atualização de tarefas.
 */
class ToggleTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    
    /**
     * Executa o caso de uso para alternar o status de uma tarefa.
     * @param task - Tarefa a ser atualizada
     */
    suspend operator fun invoke(task: Task) {
        repository.toggleTask(task)
    }
}
