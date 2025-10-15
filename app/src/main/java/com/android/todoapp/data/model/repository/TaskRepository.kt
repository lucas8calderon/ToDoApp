package com.android.todoapp.data.model.repository

import com.android.todoapp.data.model.Task

class TaskRepository private constructor() {
    private val tasks = mutableListOf<Task>()
    
    companion object {
        @Volatile
        private var INSTANCE: TaskRepository? = null
        
        fun getInstance(): TaskRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: TaskRepository().also { INSTANCE = it }
            }
        }
    }

    fun getTasks(): List<Task> = tasks

    fun addTask(task: Task) {
        tasks.add(task)
    }

    fun removeTask(task: Task) {
        tasks.remove(task)
    }

    fun toggleTask(task: Task) {
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index != -1) {
            val updated = task.copy(isDone = !task.isDone)
            tasks[index] = updated

        }
    }
}