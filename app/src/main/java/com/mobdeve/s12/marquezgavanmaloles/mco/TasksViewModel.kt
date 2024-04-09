package com.mobdeve.s12.marquezgavanmaloles.mco

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobdeve.s12.marquezgavanmaloles.mco.model.DatabaseHelper
import com.mobdeve.s12.marquezgavanmaloles.mco.model.Task

class TasksViewModel(private val databaseHelper: DatabaseHelper): ViewModel() {
    var tasks = mutableListOf<Task>()

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertTask(task: Task){
        databaseHelper.insertTask(task)
        tasks = databaseHelper.getAllTasks()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllTasks(){
        tasks = databaseHelper.getAllTasks()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteTask(taskId: Int){
        databaseHelper.deleteTask(taskId)
        tasks = databaseHelper.getAllTasks()
    }
}