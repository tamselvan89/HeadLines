package com.newsapp.app.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.app.data.model.Country
import com.newsapp.app.databinding.ListItemCountryBinding

class CountryAdapter(
    private val context: Context,
    private val OnCountryClick: (country: String) -> Unit
) :
    ListAdapter<Country, CountryAdapter.CountryViewHodler>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHodler {
        val inflater = LayoutInflater.from(parent.context)
        return CountryViewHodler(ListItemCountryBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: CountryViewHodler, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class CountryViewHodler(private val binding: ListItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(value: Country) {
            binding.country = value
            binding.txtCountry.setOnClickListener {
                OnCountryClick(value.code)
            }
            binding.executePendingBindings()
        }
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Country>() {

    override fun areContentsTheSame(oldItem: Country, newItem: Country) = true

    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem.name.equals(newItem.name)
    }
}