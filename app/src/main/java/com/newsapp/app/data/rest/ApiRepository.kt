package com.newsapp.app.data.rest

import com.newsapp.app.data.model.NewsResponse
import io.reactivex.Single
import javax.inject.Inject

class ApiRepository @Inject constructor(apiService: ApiService) {

    var apiService: ApiService? = apiService

    fun getHeadlines(param: HashMap<String?, String?>): Single<NewsResponse>? {
        return apiService?.getHeadLines(param)
    }
}