package com.example.td2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TasksViewModel : ViewModel(){


    val taskListLiveData = MutableLiveData<List<Task>?>()
    private val repository = TasksRepository()

    fun loadTasks(){
        viewModelScope.launch {
            val taskList = repository.loadTasks()
            taskListLiveData.postValue(taskList)
        }
    }



    fun deleteTask(task: Task){
        viewModelScope.launch {
            val newTask = repository.deleteTask(task)
            if (newTask) {
                val newList = taskListLiveData.value.orEmpty().toMutableList()
                newList.remove(task)
                taskListLiveData.postValue(newList)
            }
        }

    }


    fun editTask(task: Task) {
        viewModelScope.launch {
            val newTask = repository.editTask(task)
            if (newTask != null) {
                val newList = taskListLiveData.value.orEmpty().toMutableList()
                val position = newList.indexOfFirst { it.id == newTask.id }
                newList[position] = newTask
                taskListLiveData.postValue(newList)
            }
        }

    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            val newTask = repository.addTask(task)
            if (newTask != null) {
                val newList = taskListLiveData.value.orEmpty().toMutableList()
                newList.add(newTask)
                taskListLiveData.postValue(newList)
            }


        }

    }
}