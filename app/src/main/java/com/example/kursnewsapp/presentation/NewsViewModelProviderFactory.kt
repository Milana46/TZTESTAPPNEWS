package com.example.kursnewsapp.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kursnewsapp.domain.NewsInterface
import com.example.kursnewsapp.usecasses.HeadlinesCase


class NewsViewModelProviderFactory(
    val app: Application,
    private val newsRepository: NewsInterface
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, newsRepository, HeadlinesCase()) as T
    }
}
