package com.example.kursnewsapp.data.db

import androidx.room.TypeConverter
import com.example.kursnewsapp.data.Source


class Converters {

    @TypeConverter
     fun convertFromSource(source: Source):String{
         return source.name
     }

    @TypeConverter
    fun convertToSource(name:String): Source {
        return Source(name,name)
    }
}