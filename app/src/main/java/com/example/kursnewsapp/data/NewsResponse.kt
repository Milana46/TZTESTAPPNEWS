package com.example.kursnewsapp.data

import com.example.kursnewsapp.domain.ArticleModel

data class NewsResponse(
    var articles: MutableList<ArticleModel>,
    val status: String,
    val totalResults: Int,
)