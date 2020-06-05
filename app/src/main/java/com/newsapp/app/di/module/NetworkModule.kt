package com.newsapp.app.di.module

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.newsapp.app.BaseApplication.Companion.networkListener
import com.newsapp.app.BuildConfig
import com.newsapp.app.utils.CommonUtil
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    val BASE_URL: String = "http://newsapi.org/v2/"

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient?): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor?): OkHttpClient {
        val REQUEST_TIMEOUT = 60
        val httpClient = OkHttpClient().newBuilder()
        httpClient.connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(httpLoggingInterceptor!!)
        }

        /*httpClient.addInterceptor(object : NetworkConnectionInterceptor() {
            val isNetworkAvailable: Boolean
                get() = CommonUtil.isNetworkAvailable(AppManager.baseContext)

            fun onNetworkUnavailable() {
                if (networkListener != null) {
                    networkListener.onNetworkUnavailable()
                }
            }
        })*/
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return httpLoggingInterceptor
    }

}