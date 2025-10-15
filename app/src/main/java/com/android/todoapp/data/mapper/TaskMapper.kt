package com.android.todoapp.data.mapper

import com.android.todoapp.data.local.TaskEntity
import com.android.todoapp.domain.model.Task

object TaskMapper {

    fun entityToTask(entity: TaskEntity): Task {
        return Task(
            id = entity.id,
            title = entity.title,
            isDone = entity.isDone
        )
    }

    fun taskToEntity(task: Task): TaskEntity {
        return TaskEntity(
            id = task.id,
            title = task.title,
            isDone = task.isDone
        )
    }

    fun entityListToTaskList(entities: List<TaskEntity>): List<Task> {
        return entities.map { entityToTask(it) }
    }

    fun taskListToEntityList(tasks: List<Task>): List<TaskEntity> {
        return tasks.map { taskToEntity(it) }
    }
}