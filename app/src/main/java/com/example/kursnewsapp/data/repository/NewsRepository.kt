package com.example.kursnewsapp.data.repository


import com.example.kursnewsapp.data.api.RetrofitInstance.Companion.api
import com.example.kursnewsapp.data.db.DataBaseArticle
import com.example.kursnewsapp.data.db.Article
import com.example.kursnewsapp.domain.NewsInterface
import kotlinx.coroutines.flow.Flow


class NewsRepository(private val db: DataBaseArticle) : NewsInterface {


    override suspend fun getHeadlines(countryCode: String, pageNumber: Int) = api.getHeadlines(countryCode, pageNumber)

    override suspend fun isArticleFavorite(articleId: Int): Boolean {
        return db.dao().isArticleFavorite(articleId)
    }

    override suspend fun insertArticle(article: Article) = db.dao().insertArticle(article)
    override fun getAllArticle(): Flow<List<Article>> {
        return db.dao().getAllArticle()
    }

    override suspend fun deleteArticle(article: Article) = db.dao().deleteArticle(article)
}
