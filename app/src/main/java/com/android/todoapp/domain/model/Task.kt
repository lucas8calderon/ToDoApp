package com.android.todoapp.domain.model

data class Task(
    val id: Int,
    val title: String,
    var isDone: Boolean = false
)