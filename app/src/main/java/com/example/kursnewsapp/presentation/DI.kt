package com.example.kursnewsapp.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.kursnewsapp.data.db.DataBaseArticle
import com.example.kursnewsapp.data.repository.NewsRepository

class DI {
    companion object {
        fun inject(app: Application, activity: NewsActivity) {
            val newsRepository = NewsRepository(DataBaseArticle.getDatabase(activity))
            val viewModelProviderFactory = NewsViewModelProviderFactory(app, newsRepository)
            activity.newsViewModel = ViewModelProvider(activity, viewModelProviderFactory).get(NewsViewModel::class.java)
        }
    }
}