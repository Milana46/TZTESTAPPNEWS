package com.example.kursnewsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class DataBaseArticle : RoomDatabase() {

    abstract fun dao(): ArticleDao

    companion object {

        @Volatile
        private var INSTANCE: DataBaseArticle? = null

        fun getDatabase(context: Context): DataBaseArticle {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBaseArticle::class.java,
                    "tasks"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}
