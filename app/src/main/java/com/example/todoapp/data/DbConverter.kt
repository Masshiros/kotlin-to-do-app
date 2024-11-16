package com.example.todoapp.data

import androidx.room.TypeConverter
import com.example.todoapp.data.models.EPriority

class DbConverter {
    @TypeConverter
    fun mapFromPriority(priority: EPriority): String{
        return priority.name
    }
    @TypeConverter
    fun mapToPriority(priority: String): EPriority{
        return EPriority.valueOf(priority)
    }
}