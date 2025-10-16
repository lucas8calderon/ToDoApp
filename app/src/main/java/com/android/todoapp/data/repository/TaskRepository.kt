package com.android.todoapp.data.repository

import com.android.todoapp.data.local.AppDatabase
import com.android.todoapp.domain.model.Task
import com.android.todoapp.domain.repository.TaskRepository as TaskRepositoryInterface
import com.android.todoapp.data.mapper.TaskMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementação concreta do TaskRepository.
 * Responsável por gerenciar dados de tarefas usando Room Database.
 */
class TaskRepository @Inject constructor(
    private val database: AppDatabase
) : TaskRepositoryInterface {

    private val todoDao = database.todoDao()

    override fun getTasks(): Flow<List<Task>> {
        return todoDao.getAllTodos().map { entities ->
            TaskMapper.entityListToTaskList(entities)
        }
    }

    override suspend fun addTask(task: Task) {
        val entity = TaskMapper.taskToEntity(task)
        todoDao.insert(entity)
    }

    override suspend fun removeTask(task: Task) {
        val entity = TaskMapper.taskToEntity(task)
        todoDao.delete(entity)
    }

    override suspend fun toggleTask(task: Task) {
        val entity = TaskMapper.taskToEntity(task.copy(isDone = !task.isDone))
        todoDao.update(entity)
    }
}