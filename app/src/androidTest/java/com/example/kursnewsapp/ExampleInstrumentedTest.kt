package com.example.kursnewsapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.kursnewsapp.data.api.RetrofitInstance
import com.example.kursnewsapp.data.api.newsAPI
import com.example.kursnewsapp.presentation.util.Constants

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiTest {

    private lateinit var Api: newsAPI


    @Before
    fun setUp() {
        val client=OkHttpClient.Builder().build()

        val ret= Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val api by lazy {
            ret.create(newsAPI::class.java)
        }
        Api=api

    }

    @Test
    fun testGetHeadlines_Success() = runBlocking {
       assert(Api.getHeadlines().isSuccessful)

    }




}

//@RunWith(AndroidJUnit4::class)
//class ExampleInstrumentedTest {
//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.example.kursnewsapp", appContext.packageName)
//    }
//}