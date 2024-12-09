package com.example.kursnewsapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kursnewsapp.R
import com.example.kursnewsapp.data.db.Article
import com.example.kursnewsapp.domain.ArticleModel

class NewsAdapter(private val context: Context) :
    RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val articleImage: ImageView = itemView.findViewById(R.id.articleImage)
        val articleSource: TextView = itemView.findViewById(R.id.articleSource)
        val articleTitle: TextView = itemView.findViewById(R.id.articleTitle)
        val articleDescription: TextView = itemView.findViewById(R.id.articleDescription)
        val articleDateTime: TextView = itemView.findViewById(R.id.articleDateTime)

        init {
            itemView.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(differ.currentList[adapterPosition])
                }
            }
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<ArticleModel>() {
        override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((ArticleModel) -> Unit)? = null

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val listNews = differ.currentList[position]
        holder.apply {
            Glide.with(context).load(listNews.urlToImage).into(articleImage)
            articleSource.text = listNews.source?.name  //source.name
            articleTitle.text = listNews.title
            articleDescription.text = listNews.description
            articleDateTime.text = listNews.publishedAt
        }
    }


    fun setOnItemClickListener(listener: (ArticleModel) -> Unit) {
        onItemClickListener = listener
    }
}
