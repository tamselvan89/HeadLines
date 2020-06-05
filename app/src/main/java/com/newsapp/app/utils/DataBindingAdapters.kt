package com.newsapp.app.utils

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.newsapp.app.R

@BindingAdapter("app:firstOrBlank", requireAll = false)
fun TextView.firstOrBlank(value: String?) {
    text = value ?: ""
}

@BindingAdapter("app:imageUrl")
fun ImageView.loadImage(imageUrl: String?) {
    Glide.with(this).load(imageUrl).placeholder(R.drawable.no_img).into(this)
}
