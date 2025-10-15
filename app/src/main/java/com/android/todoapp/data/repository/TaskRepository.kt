package com.android.todoapp.data.repository

import android.content.Context
import com.android.todoapp.data.local.AppDatabase
import com.android.todoapp.domain.model.Task
import com.android.todoapp.data.mapper.TaskMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository private constructor(context: Context) {

    private val todoDao = AppDatabase.getDatabase(context).todoDao()

    companion object {
        @Volatile
        private var INSTANCE: TaskRepository? = null

        fun getInstance(context: Context): TaskRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: TaskRepository(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    fun getTasks(): Flow<List<Task>> {
        return todoDao.getAllTodos().map { entities ->
            TaskMapper.entityListToTaskList(entities)
        }
    }

    suspend fun addTask(task: Task) {
        val entity = TaskMapper.taskToEntity(task)
        todoDao.insert(entity)
    }

    suspend fun removeTask(task: Task) {
        val entity = TaskMapper.taskToEntity(task)
        todoDao.delete(entity)
    }

    suspend fun toggleTask(task: Task) {
        val entity = TaskMapper.taskToEntity(task.copy(isDone = !task.isDone))
        todoDao.update(entity)
    }
}