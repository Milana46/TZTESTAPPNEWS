package com.example.kursnewsapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.kursnewsapp.R
import com.example.kursnewsapp.databinding.ActivityNewsBinding
import com.example.kursnewsapp.data.db.DataBaseArticle
import com.example.kursnewsapp.data.repository.NewsRepository
import retrofit2.Retrofit

class NewsActivity : AppCompatActivity() {

    lateinit var newsViewModel: NewsViewModel
    private lateinit var binding:ActivityNewsBinding
    lateinit var retr:Retrofit


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val newsRepository= NewsRepository(DataBaseArticle.getDatabase(this))
        val viewModelProviderFactory= NewsViewModelProviderFactory(application,newsRepository)
        newsViewModel=ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)


        val navHostFragment=supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController=navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

    }


}