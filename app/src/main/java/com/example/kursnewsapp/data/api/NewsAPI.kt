package com.example.kursnewsapp.data.api


import com.example.kursnewsapp.BuildConfig.API_KEY
import com.example.kursnewsapp.data.NewsResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query


interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String =API_KEY
    ): Response<NewsResponse>
}

