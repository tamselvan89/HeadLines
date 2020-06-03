package com.newsapp.app.data.rest

import com.newsapp.app.data.model.NewsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("top-headlines")
    fun getHeadLines(@QueryMap param: HashMap<String?, String?>): Single<NewsResponse>?
}