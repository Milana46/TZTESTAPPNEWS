package com.example.kursnewsapp.usecasses

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kursnewsapp.presentation.NewsViewModel
import com.example.kursnewsapp.presentation.util.Constants

class HeadlinesScroll {

    companion object {

        fun headLinesScroll(
            recyclerView: RecyclerView,
            isError: Boolean,
            isLoading: Boolean,
            isLastPage: Boolean,
            isScrolling: Boolean
        ): Boolean {

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNoErrors = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate =
                isNoErrors && isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling

            return shouldPaginate


        }
    }
}