package com.android.todoapp

data class Todo(
    val id: Int,
    val title: String,
    var isDone: Boolean = false
)
