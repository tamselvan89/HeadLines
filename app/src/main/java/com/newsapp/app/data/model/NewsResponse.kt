package com.newsapp.app.data.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(

    @SerializedName("status")
    val status: String,

    @SerializedName("totalResults")
    val totalResults: Int,

    @SerializedName("articles")
    val articles: List<Article>
)