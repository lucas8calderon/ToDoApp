package com.android.todoapp.domain.repository

import com.android.todoapp.domain.model.Task
import kotlinx.coroutines.flow.Flow

/**
 * Interface que define o contrato para operações de dados de tarefas.
 * Segue o princípio de inversão de dependência (DIP) do SOLID.
 */
interface TaskRepository {
    fun getTasks(): Flow<List<Task>>

    suspend fun addTask(task: Task)

    suspend fun removeTask(task: Task)

    suspend fun toggleTask(task: Task)
}