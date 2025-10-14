package com.android.todoapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.todoapp.Todo
import com.android.todoapp.databinding.ItemTodoBinding

class TodoAdapter(
    private val todos: MutableList<Todo>,
    private val onDelete: (Todo) -> Unit,
    private val onToggle: (Todo) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun getItemCount() = todos.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos[position]
        holder.binding.textTitle.text = todo.title
        holder.binding.checkDone.isChecked = todo.isDone

        holder.binding.checkDone.setOnCheckedChangeListener { _, isChecked ->
            todo.isDone = isChecked
            onToggle(todo)
        }

        holder.binding.btnDelete.setOnClickListener {
            onDelete(todo)
        }
    }

    fun addTodo(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun removeTodo(todo: Todo) {
        val index = todos.indexOf(todo)
        if (index != -1) {
            todos.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}