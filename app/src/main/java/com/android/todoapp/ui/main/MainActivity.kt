package com.android.todoapp.ui.main

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.todoapp.data.model.Task
import com.android.todoapp.data.model.repository.TaskRepository
import com.android.todoapp.databinding.ActivityMainBinding
import com.android.todoapp.ui.main.adapter.TodoAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TodoAdapter
    private val repository = TaskRepository()
    private var nextTaskId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupFab()
    }

    private fun setupRecyclerView() {
        adapter = TodoAdapter(
            repository.getTasks().toMutableList(),
            onToggle = {task ->
                repository.toggleTask(task)
                refreshList()
            },
            onDelete = { task ->
                repository.removeTask(task)
                refreshList()
                Snackbar.make(binding.root, "Tarefa removida", Snackbar.LENGTH_SHORT).show()
            }
        )

        binding.recyclerTodos.layoutManager = LinearLayoutManager(this)
        binding.recyclerTodos.adapter = adapter
    }

    private fun setupFab() {
        binding.fabAddTodo.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val input = EditText(this)
        input.hint = "Digite o título da tarefa"

        MaterialAlertDialogBuilder(this)
            .setTitle("Nova tarefa")
            .setView(input)
            .setPositiveButton("Adicionar") { dialog, _ ->
                val title = input.text.toString()
                val task = Task(id = nextTaskId++, title)

                if (title.isBlank()) {
                    Snackbar.make(binding.root, "O título não pode estar vazio!", Snackbar.LENGTH_SHORT).show()
                } else {
                    repository.addTask(task)
                    refreshList()
                    Snackbar.make(binding.root, "Tarefa adicionada!", Snackbar.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
            .show()
    }


    private fun refreshList() {
        binding.recyclerTodos.post {
            if (!binding.recyclerTodos.isComputingLayout) {
                adapter.updateData(repository.getTasks())
            }
        }
    }
}