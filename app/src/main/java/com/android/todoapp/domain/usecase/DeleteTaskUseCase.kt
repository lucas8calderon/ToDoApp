package com.android.todoapp.domain.usecase

import com.android.todoapp.domain.model.Task
import com.android.todoapp.domain.repository.TaskRepository
import javax.inject.Inject

/**
 * Use Case para deletar uma tarefa.
 * Encapsula a lógica de negócio para remoção de tarefas.
 */
class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    
    /**
     * Executa o caso de uso para deletar uma tarefa.
     * @param task - Tarefa a ser deletada
     */
    suspend operator fun invoke(task: Task) {
        repository.removeTask(task)
    }
}
