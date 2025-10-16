package com.android.todoapp.domain.repository

import com.android.todoapp.domain.model.Task
import kotlinx.coroutines.flow.Flow

/**
 * Interface que define o contrato para operações de dados de tarefas.
 * Segue o princípio de inversão de dependência (DIP) do SOLID.
 */
interface TaskRepository {
    
    /**
     * Obtém todas as tarefas como um Flow reativo.
     * @return Flow<List<Task>> - Lista observável de tarefas
     */
    fun getTasks(): Flow<List<Task>>
    
    /**
     * Adiciona uma nova tarefa.
     * @param task - Tarefa a ser adicionada
     */
    suspend fun addTask(task: Task)
    
    /**
     * Remove uma tarefa existente.
     * @param task - Tarefa a ser removida
     */
    suspend fun removeTask(task: Task)
    
    /**
     * Alterna o status de conclusão de uma tarefa.
     * @param task - Tarefa a ser atualizada
     */
    suspend fun toggleTask(task: Task)
}
