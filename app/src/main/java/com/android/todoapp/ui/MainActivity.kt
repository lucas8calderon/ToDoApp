package com.android.todoapp.ui

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.todoapp.Todo
import com.android.todoapp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TodoAdapter
    private val todos = mutableListOf<Todo>()
    private var nextId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TodoAdapter(
            todos,
            onDelete = { deleteTodo(it) },
            onToggle = { toggleTodo(it) }
        )

        binding.recyclerTodos.adapter = adapter

        binding.fabAddTodo.setOnClickListener {
            showAddTodoDialog(this)
        }
    }

    private fun showAddTodoDialog(context: Context) {
        val input = EditText(context)
        input.hint = "Digite a tarefa"

        MaterialAlertDialogBuilder(context)
            .setTitle("Nova Tarefa")
            .setView(input)
            .setPositiveButton("Adicionar") { _, _ ->
                val title = input.text.toString()
                if (title.isNotBlank()) {
                    val todo = Todo(id = nextId++, title = title)
                    adapter.addTodo(todo)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun deleteTodo(todo: Todo) {
        adapter.removeTodo(todo)
    }

    private fun toggleTodo(todo: Todo) {
        Toast.makeText(this, "Tarefa concluida.", Toast.LENGTH_SHORT).show()
    }
}