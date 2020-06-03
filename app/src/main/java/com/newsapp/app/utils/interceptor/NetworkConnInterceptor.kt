package com.newsapp.app.utils.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

abstract class NetworkConnInterceptor : Interceptor {

    abstract fun IsNetworkAvailable(): Boolean

    abstract fun OnNetworkFail()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        if (!IsNetworkAvailable()) {
            OnNetworkFail()
        }
        return chain.proceed(request)
    }

}