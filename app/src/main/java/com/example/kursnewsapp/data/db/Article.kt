package com.example.kursnewsapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kursnewsapp.domain.ArticleModel
import java.io.Serializable


@Entity(tableName = "article")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val key: Int,

    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
) : Serializable {
    fun toModel(): ArticleModel {
        return ArticleModel(
            author,
            content,
            description,
            publishedAt,
            source,
            title,
            url,
            urlToImage
        )
    }
}


