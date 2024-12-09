package com.example.kursnewsapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article): Long

    @Query("SELECT * FROM article")
    fun getAllArticle(): Flow<List<Article>>

    @Query("SELECT COUNT(*) > 0 FROM article WHERE [key] = :articleId")
    suspend fun isArticleFavorite(articleId: Int): Boolean

    @Delete
    suspend fun deleteArticle(article: Article)
}
