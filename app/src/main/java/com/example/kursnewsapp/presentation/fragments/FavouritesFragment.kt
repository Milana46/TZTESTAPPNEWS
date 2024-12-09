package com.example.kursnewsapp.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kursnewsapp.R
import com.example.kursnewsapp.presentation.adapters.NewsAdapter
import com.example.kursnewsapp.databinding.FragmentFavouritesBinding
import com.example.kursnewsapp.presentation.NewsActivity
import com.example.kursnewsapp.presentation.NewsViewModel
import com.example.kursnewsapp.presentation.util.Constants.Companion.ARTICLE_KEY
import kotlinx.coroutines.launch

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: FragmentFavouritesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavouritesBinding.bind(view)

        newsViewModel = (activity as NewsActivity).newsViewModel
        setupFavoritesRecycler()

        newsAdapter.setOnItemClickListener { article ->
            val bundle = Bundle()
            bundle.putSerializable(ARTICLE_KEY, article)
            findNavController().navigate(R.id.action_favouritesFragment_to_articleFragment, bundle)
        }

        lifecycleScope.launch {
            newsViewModel.getAll().collect { articles ->
                newsAdapter.differ.submitList(articles.map { it.toModel() })
            }
        }
    }

    private fun setupFavoritesRecycler() {
        newsAdapter = NewsAdapter(requireContext())
        binding.rvFavourites.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
