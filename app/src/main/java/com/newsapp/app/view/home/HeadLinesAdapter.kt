package com.newsapp.app.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.app.data.model.Article
import com.newsapp.app.databinding.ListItemHeadlinesBinding

class HeadLinesAdapter(private val OnItemClick: (url: String) -> Unit) :
    ListAdapter<Article, HeadLinesAdapter.HeadLinesViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadLinesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HeadLinesViewHolder(ListItemHeadlinesBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: HeadLinesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class HeadLinesViewHolder(private val binding: ListItemHeadlinesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.article = article
            binding.executePendingBindings()

            binding.rootLayout.setOnClickListener {
                OnItemClick(article.url)
            }
        }
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {

    override fun areContentsTheSame(oldItem: Article, newItem: Article) = true

    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.title.equals(newItem.title)
    }
}