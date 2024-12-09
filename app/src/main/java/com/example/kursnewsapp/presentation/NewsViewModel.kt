package com.example.kursnewsapp.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kursnewsapp.data.db.Article
import com.example.kursnewsapp.data.NewsResponse
import com.example.kursnewsapp.presentation.util.Resource
import com.example.kursnewsapp.domain.NewsInterface
import com.example.kursnewsapp.usecasses.HeadlinesCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel(app: Application, private val newsRepository: NewsInterface, private val headlinesCase: HeadlinesCase) :
    AndroidViewModel(app) {

    val headlines: StateFlow<Resource<NewsResponse>> = headlinesCase.headlines()

    private val _snackbarMessage = MutableStateFlow<String>("")

    init {
        getHeadlines("us")
    }

    private fun getHeadlinesContext(context: Context, countryCode: String) = viewModelScope.launch {
        headlinesCase.headlinesInternet(context, newsRepository, countryCode)
    }

    fun getHeadlines(countryCode: String) {
        getHeadlinesContext(this.getApplication(), countryCode)
    }

    fun getAll() = newsRepository.getAllArticle()
    fun page() = headlinesCase.headlinesPage

    fun toggleFavorite(article: Article) = viewModelScope.launch {
        val isFavorite = newsRepository.isArticleFavorite(article.key ?: -1)
        if (isFavorite) {
            newsRepository.deleteArticle(article)
            _snackbarMessage.value = "Removed from favorites"
        } else {
            newsRepository.insertArticle(article)
            _snackbarMessage.value = "Added to favorites"
        }
    }
}
