package com.example.kursnewsapp.usecasses

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.kursnewsapp.data.NewsResponse
import com.example.kursnewsapp.domain.NewsInterface
import com.example.kursnewsapp.presentation.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response
import java.io.IOException

class HeadlinesCase {
    private val _headlines = MutableStateFlow<Resource<NewsResponse>>(Resource.Loading())
    var headlinesResponse: NewsResponse? = null
    var headlinesPage = 1

    fun headlines(): MutableStateFlow<Resource<NewsResponse>> {
        return _headlines
    }

    fun internetConnection(context: Context): Boolean {
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            return getNetworkCapabilities(activeNetwork)?.run {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } ?: false
        }
    }

    private fun handleHeadlineResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                headlinesPage++
                if (headlinesResponse == null) {
                    headlinesResponse = resultResponse
                } else {
                    val oldArticles = headlinesResponse?.articles ?: mutableListOf()
                    val newArticles = resultResponse.articles
                    oldArticles.addAll(newArticles)
                    headlinesResponse?.articles = oldArticles
                }
                return Resource.Success(headlinesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    suspend fun headlinesInternet(context: Context, repo: NewsInterface, countryCode: String) {
        _headlines.value = Resource.Loading()
        try {
            if (internetConnection(context)) {
                val response = repo.getHeadlines(countryCode, this.headlinesPage)
                _headlines.value = handleHeadlineResponse(response)
            } else {
                _headlines.value = Resource.Error("No Internet connection")
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _headlines.value = Resource.Error("Unable to connect")
                else -> _headlines.value = Resource.Error("No signal")
            }
        }
    }
}