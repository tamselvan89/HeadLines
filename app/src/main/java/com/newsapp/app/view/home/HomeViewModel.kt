package com.newsapp.app.view.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.newsapp.app.data.model.NewsResponse
import com.newsapp.app.data.prefs.PreferencesHelper
import com.newsapp.app.data.rest.ApiService
import com.newsapp.app.utils.rx.BaseSchedulerProvider
import com.newsapp.app.view.base.BaseViewModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class HomeViewModel @Inject constructor(
    compositeDisposable: CompositeDisposable,
    baseSchedulerProvider: BaseSchedulerProvider,
    preferencesHelper: PreferencesHelper,
    apiService: ApiService,
    application: Application
) : BaseViewModel(
    compositeDisposable, baseSchedulerProvider, preferencesHelper, apiService, application
) {
    private val newsResponse = MutableLiveData<NewsResponse>()

    fun getNewsResponse() = newsResponse

    fun loadData(category: String) {
        val param = HashMap<String?, String?>()
        param.put("country", "in")
        param.put("category", category.toLowerCase(Locale.ROOT))
        param.put("apiKey", "fdb429f4a1eb4aeb93110a6f1f05167c")
        getApiService().getHeadLines(param)
            ?.subscribeOn(
                getBaseSchedulerProvider()
                    ?.io()
            )
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<NewsResponse?> {
                override fun onSubscribe(disposable: Disposable) {
                    getCompositeDisposal()?.add(disposable)
                }

                override fun onSuccess(response: NewsResponse) {
                    newsResponse.value = response
                }

                override fun onError(err: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }
}