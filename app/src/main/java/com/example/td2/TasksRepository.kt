package com.example.td2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.td2.network.Api
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class TasksRepository {
    private val taskService = Api.taskService
    private val coroutineScope = MainScope()

    fun getTasks(): LiveData<List<Task>?> {
        val tasks = MutableLiveData<List<Task>?>()
        coroutineScope.launch { tasks.postValue(loadTasks()) }
        return tasks
    }

    fun deleteTask(id: String): MutableLiveData<Boolean> {
        val success = MutableLiveData<Boolean>()
        coroutineScope.launch { success.postValue(deleteTaskSuspend(id)) }
        return success
    }

    private suspend fun deleteTaskSuspend(id: String): Boolean {
        val res = taskService.deleteTask(id)
        return res.isSuccessful

    }


    suspend fun loadTasks(): List<Task>? {
        val tasksResponse = taskService.getTasks()
        return if (tasksResponse.isSuccessful) tasksResponse.body() else null
    }
}