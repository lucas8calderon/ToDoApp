package com.android.todoapp.data.model.repository

import com.android.todoapp.data.model.Task

class TaskRepository {
    private val tasks = mutableListOf<Task>()

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