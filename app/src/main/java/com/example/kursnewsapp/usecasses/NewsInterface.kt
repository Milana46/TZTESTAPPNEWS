package com.example.kursnewsapp.usecasses

import androidx.lifecycle.LiveData
import com.example.kursnewsapp.data.NewsResponse
import com.example.kursnewsapp.domain.Article
import retrofit2.Response

interface NewsInterface  {

    suspend fun getHeadlines(countryCode:String, pageNumber: Int): Response<NewsResponse>
    suspend fun isArticleFavorite(articleId: Int): Boolean

    suspend fun insertArticle(article: Article):Long
    fun getAllArticle(): LiveData<List<Article>>
    suspend fun deleteArticle(article: Article)

}