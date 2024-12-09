package com.example.kursnewsapp.domain

import com.example.kursnewsapp.data.NewsResponse
import com.example.kursnewsapp.data.db.Article
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsInterface  {

    suspend fun getHeadlines(countryCode:String, pageNumber: Int): Response<NewsResponse>
    suspend fun isArticleFavorite(articleId: Int): Boolean

    suspend fun insertArticle(article: Article):Long
    fun getAllArticle(): Flow<List<Article>>
    suspend fun deleteArticle(article: Article)
}