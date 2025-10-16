package com.android.todoapp.domain.usecase

import com.android.todoapp.domain.model.Task
import com.android.todoapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use Case para obter todas as tarefas.
 * Encapsula a lógica de negócio para recuperar tarefas.
 */
class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    
    /**
     * Executa o caso de uso para obter todas as tarefas.
     * @return Flow<List<Task>> - Lista observável de tarefas
     */
    operator fun invoke(): Flow<List<Task>> {
        return repository.getTasks()
    }
}
