package com.example.kursnewsapp.presentation.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AbsListView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kursnewsapp.R
import com.example.kursnewsapp.R.id.action_headlinesFragment_to_articleFragment
import com.example.kursnewsapp.presentation.adapters.NewsAdapter
import com.example.kursnewsapp.databinding.FragmentHeadlinesBinding
import com.example.kursnewsapp.presentation.NewsActivity
import com.example.kursnewsapp.presentation.NewsViewModel
import com.example.kursnewsapp.presentation.util.Constants
import com.example.kursnewsapp.presentation.util.Resource
import com.example.kursnewsapp.usecasses.HeadlinesScroll

@Suppress("NAME_SHADOWING")
class HeadlinesFragment : Fragment(R.layout.fragment_headlines) {
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var retryButton: Button
    private lateinit var errorText: TextView
    private lateinit var itemHeadlinesError: CardView
    private lateinit var binding: FragmentHeadlinesBinding

    private var isError = false
    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHeadlinesBinding.bind(view)

        itemHeadlinesError = view.findViewById(R.id.itemHeadlinesError)

        val inflater =
            requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.item_error, null)

        retryButton = view.findViewById(R.id.retryButton)
        errorText = view.findViewById(R.id.errorText)

        newsViewModel = (activity as NewsActivity).newsViewModel
        setUpHeadLinesRv()

        newsAdapter.setOnItemClickListener() {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }

            findNavController().navigate(action_headlinesFragment_to_articleFragment, bundle)
        }

        lifecycleScope.launchWhenStarted {
            newsViewModel.headlines.collect { response ->
                when (response) {
                    is Resource.Success<*> -> {
                        hideProgressBar()
                        hideErrorMessage()
                        response.data?.let { newsResponse ->
                            newsAdapter.differ.submitList(newsResponse.articles.toList())
                            val totalPages = newsResponse.totalResults / Constants.QUERY_PAGE_SIZE + 2
                            isLastPage = newsViewModel.page() == totalPages

                            if (isLastPage) {
                                binding.recyclerHeadlines.setPadding(0, 0, 0, 0)
                            }
                        }
                    }

                    is Resource.Error<*> -> {
                        hideProgressBar()
                        response.message?.let { message ->
                            Toast.makeText(activity, "Sorry error $message", Toast.LENGTH_SHORT).show()
                            showErrorMessage(message)
                        }
                    }

                    is Resource.Loading<*> -> {
                        showProgressBar()
                    }

                }
            }
        }


        retryButton.setOnClickListener {
            newsViewModel.getHeadlines("us")
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideErrorMessage() {
        itemHeadlinesError.visibility = View.INVISIBLE
        isError = false
    }

    private fun showErrorMessage(message: String) {
        itemHeadlinesError.visibility = View.VISIBLE
        errorText.text = message
        isError = true
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val shouldPaginate=HeadlinesScroll.headLinesScroll(recyclerView,
                isError,
                isLoading,
                isLastPage,
                isScrolling)

            if (shouldPaginate) {
                newsViewModel.getHeadlines("us")
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            isScrolling = newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL
        }
    }

    private fun setUpHeadLinesRv() {
        newsAdapter = NewsAdapter(requireContext())
        binding.recyclerHeadlines.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(scrollListener)
        }
    }
}


