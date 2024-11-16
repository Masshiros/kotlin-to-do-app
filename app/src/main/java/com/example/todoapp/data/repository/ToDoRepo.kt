package com.example.todoapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.todoapp.data.ToDoDao
import com.example.todoapp.data.models.ToDoEntity

class ToDoRepo(private val toDoDao: ToDoDao) {
    val getAllData: LiveData<List<ToDoEntity>> = toDoDao.getAllData()
    val sortByHighPriority: LiveData<List<ToDoEntity>> = toDoDao.sortByHighPriority()
    val sortByLowPriority: LiveData<List<ToDoEntity>> = toDoDao.sortByLowPriority()

    suspend fun insertData(toDoData: ToDoEntity): Long{
        try{
            Log.d("ToDoVM", "Data inserted successfully")
           return toDoDao.insertData(toDoData)

        }catch(e:Exception){
            Log.e("ToDoVM", "Error inserting data", e)
            return 0
        }

    }

    suspend fun updateData(toDoData: ToDoEntity){
        toDoDao.updateData(toDoData)
    }

    suspend fun deleteItem(toDoData: ToDoEntity){
        toDoDao.deleteItem(toDoData)
    }

    suspend fun deleteAll(){
        toDoDao.deleteAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ToDoEntity>> {
        return toDoDao.searchDatabase(searchQuery)
    }
}