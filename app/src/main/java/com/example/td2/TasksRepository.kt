package com.example.td2

import com.example.td2.network.Api

class TasksRepository {
    private val taskService = Api.taskService



    suspend fun deleteTask(task: Task): Boolean {
        val res = taskService.deleteTask(task.id)
        return res.isSuccessful

    }


    suspend fun loadTasks(): List<Task>? {
        val tasksResponse = taskService.getTasks()
        return if (tasksResponse.isSuccessful) tasksResponse.body() else null
    }

    suspend fun addTask(task: Task): Task?{
        val taskResponse = taskService.createTask(task)
        return taskResponse.body()
    }

    suspend fun editTask(task: Task): Task?{
        val taskResponse = taskService.updateTask(task)
        return taskResponse.body()
    }
}
