package com.example.kursnewsapp.data

import com.example.kursnewsapp.domain.Article

data class NewsResponse(
    var articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)