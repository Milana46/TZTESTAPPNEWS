package com.example.kursnewsapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.kursnewsapp.R
import com.example.kursnewsapp.databinding.FragmentArticleBinding
import com.example.kursnewsapp.data.db.Article
import com.example.kursnewsapp.presentation.NewsActivity
import com.example.kursnewsapp.presentation.NewsViewModel
import com.example.kursnewsapp.presentation.util.Constants.Companion.ARTICLE_KEY

class ArticleFragment : Fragment(R.layout.fragment_article) {
    private lateinit var newsViewModel: NewsViewModel
    private val args: ArticleFragmentArgs by navArgs()
    private var _binding: FragmentArticleBinding? = null

    val article = arguments?.getSerializable(ARTICLE_KEY) as? Article
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel = (activity as NewsActivity).newsViewModel
        val article = args.article

        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let {
                loadUrl(it)
            }
        }

        binding.fab.setOnClickListener {
            newsViewModel.toggleFavorite(article)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
