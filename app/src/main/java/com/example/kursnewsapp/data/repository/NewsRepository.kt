package com.example.kursnewsapp.data.repository

import com.example.kursnewsapp.data.api.RetrofitInstance
import com.example.kursnewsapp.data.db.DataBaseArticle
import com.example.kursnewsapp.domain.Article
import com.example.kursnewsapp.usecasses.NewsInterface


class NewsRepository(private val db: DataBaseArticle):NewsInterface {


    override suspend fun getHeadlines(countryCode:String, pageNumber: Int)= RetrofitInstance
        .api.getHeadlines(countryCode,pageNumber)

    override suspend fun isArticleFavorite(articleId: Int): Boolean {
        return db.dao().isArticleFavorite(articleId)
    }
    override suspend fun insertArticle(article: Article)=db.dao().insertArticle(article)

     override fun getAllArticle()=db.dao().getAllArticle()

    override suspend fun deleteArticle(article: Article)=db.dao().deleteArticle(article)
}
