package com.example.kursnewsapp.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kursnewsapp.data.repository.NewsRepository
import com.example.kursnewsapp.usecasses.NewsInterface


class NewsViewModelProviderFactory(val app:Application, private val newsRepository: NewsInterface)
    :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app,newsRepository) as T
    }
}
