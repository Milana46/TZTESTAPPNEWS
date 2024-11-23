package com.example.kursnewsapp.ui.fragments

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kursnewsapp.models.Article
import com.example.kursnewsapp.models.NewsResponse
import com.example.kursnewsapp.repository.NewsRepository
import com.example.kursnewsapp.util.Resource
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response
import java.util.Locale.IsoCountryCode

class NewsViewModel(app: Application, private val newsRepository: NewsRepository) : AndroidViewModel(app) {

    val headlines: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var headlinesPage = 1

    private val _snackbarMessage = MutableLiveData<String>()
    val snackbarMessage: LiveData<String> get() = _snackbarMessage

    var headlinesResponse: NewsResponse? = null



    init{
        getHeadlines("us")
    }


    fun getHeadlines(countryCode: String)=viewModelScope.launch {
        headlinesInternet(countryCode)
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


//    fun addToFavorites(article: Article)=viewModelScope.launch {
//        newsRepository.insertArticle(article)
//
//    }
//    fun deleteNews(article:Article)=viewModelScope.launch {
//        newsRepository.deleteArticle(article)
//    }

    fun toggleFavorite(article: Article) = viewModelScope.launch {
        val isFavorite = newsRepository.isArticleFavorite(article.key ?: -1)
        System.out.println(article)
        System.out.println(isFavorite)
        if (isFavorite) {
            newsRepository.deleteArticle(article)
            _snackbarMessage.postValue("Removed from favorites")
        } else {
            newsRepository.insertArticle(article)
            _snackbarMessage.postValue("Added to favorites")
        }
    }





    fun getAll()=newsRepository.getAllArticle()


    fun internetConnection(context: Context):Boolean{
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply{
            return getNetworkCapabilities(activeNetwork)?.run{
                when{
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->true
                    else->false
                }
            }?: false
        }


    }

    private suspend fun headlinesInternet(countryCode:String){
        headlines.postValue(Resource.Loading())
        try{
            if(internetConnection(this.getApplication())){
                val response=newsRepository.getHeadlines(countryCode,headlinesPage)
                headlines.postValue(handleHeadlineResponse(response))
            }
            else{
                headlines.postValue(Resource.Error("No Internet connection"))
            }
        }
        catch(t:Throwable){
            when(t){
                is IOException->headlines.postValue(Resource.Error("Unable to connect"))
                else->headlines.postValue(Resource.Error("No signal"))
            }
        }
    }


}
