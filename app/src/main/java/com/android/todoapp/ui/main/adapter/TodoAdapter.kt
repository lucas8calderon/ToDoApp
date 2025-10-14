package com.android.todoapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.todoapp.data.model.Task
import com.android.todoapp.databinding.ItemTodoBinding

class TodoAdapter(
    private var tasks: MutableList<Task>,
    private val onDelete: (Task) -> Unit,
    private val onToggle: (Task) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {


    inner class TodoViewHolder(val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.textTitle.text = task.title
            binding.checkDone.isChecked = task.isDone

            binding.checkDone.setOnCheckedChangeListener { _, _ ->
                onToggle(task)
            }

            binding.btnDelete.setOnClickListener {
                onDelete(task)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }


    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount() = tasks.size

    fun updateData(newTasks: List<Task>) {
        tasks = newTasks.toMutableList()

        if (tasks.size <= 20) {
            notifyDataSetChanged()
        } else {
            notifyDataSetChanged()
        }
    }
}