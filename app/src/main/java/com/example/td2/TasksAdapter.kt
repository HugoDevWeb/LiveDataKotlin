package com.example.td2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_task.view.*



class TasksAdapter() : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {


    var list: List<Task> = emptyList()


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        val task = list[position]
        holder.bind(task)


    }



    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }


    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task){
            itemView.task_title.text = task.title
            itemView.task_description.text = task.description
            itemView.delete_button.setOnClickListener{onDeleteClickListener.invoke(task)}
            itemView.edit_button.setOnClickListener{onEditClickListener.invoke(task)}

        }



    }






    var onDeleteClickListener: (Task) -> Unit = {}

    var onEditClickListener: (Task) -> Unit =  {

    }

}