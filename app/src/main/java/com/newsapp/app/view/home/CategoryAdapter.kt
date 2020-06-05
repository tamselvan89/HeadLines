package com.newsapp.app.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.app.R
import com.newsapp.app.data.model.Category
import com.newsapp.app.databinding.ListItemCategoryBinding

class CategoryAdapter(
    private val context: Context,
    private val OnCategoryClick: (category: String) -> Unit
) :
    ListAdapter<Category, CategoryAdapter.CategoryViewHodler>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHodler {
        val inflater = LayoutInflater.from(parent.context)
        return CategoryViewHodler(ListItemCategoryBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHodler, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class CategoryViewHodler(private val binding: ListItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(value: Category) {
            binding.category = value
            binding.cardviewCategory.setOnClickListener {
                for (data in currentList) {
                    data.isSelected = false
                }
                value.isSelected = true
                OnCategoryClick(value.name)
                notifyDataSetChanged()
            }
            binding.executePendingBindings()

            val bgColor = if (value.isSelected) ContextCompat.getColor(
                context,
                R.color.BgCategorySelected
            ) else ContextCompat.getColor(context, R.color.BgCategoryNormal)
            binding.cardviewCategory.setCardBackgroundColor(bgColor)
        }
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Category>() {

    override fun areContentsTheSame(oldItem: Category, newItem: Category) = true

    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.name.equals(newItem.name)
    }
}