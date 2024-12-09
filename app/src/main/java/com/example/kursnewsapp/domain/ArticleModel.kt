package com.example.kursnewsapp.domain

import com.example.kursnewsapp.data.db.Source
import java.io.Serializable

class ArticleModel (
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
) : Serializable
