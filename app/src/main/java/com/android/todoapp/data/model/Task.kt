package com.android.todoapp.data.model

data class Task(
    val id: Int,
    val title: String,
    var isDone: Boolean = false
)