package com.sgrab.androidmaster.todoapp

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter (private val tasks:List<Task>) : RecyclerView.Adapter<TasksViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TasksViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        holder: TasksViewHolder,
        position: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = tasks.size
}