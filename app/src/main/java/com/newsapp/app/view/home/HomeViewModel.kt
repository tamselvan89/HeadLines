package com.newsapp.app.view.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.newsapp.app.data.model.NewsResponse
import com.newsapp.app.data.prefs.PreferencesHelper
import com.newsapp.app.data.rest.ApiService
import com.newsapp.app.generic.AppConstants.API_KEY
import com.newsapp.app.utils.rx.BaseSchedulerProvider
import com.newsapp.app.view.base.BaseViewModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    compositeDisposable: CompositeDisposable,
    baseSchedulerProvider: BaseSchedulerProvider,
    preferencesHelper: PreferencesHelper,
    apiService: ApiService,
    application: Application
) : BaseViewModel(
    compositeDisposable, baseSchedulerProvider, preferencesHelper, apiService, application
) {

    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _viewState = MutableLiveData<HomeViewState>()
    val viewState: LiveData<HomeViewState> = _viewState

    fun setInitialValue() {
        if (getPreferencesHelper().getCategory() == null) {
            getPreferencesHelper().setCategory("general")
            getPreferencesHelper().setCountry("in")
        }
    }

    fun getCountry(): String {
        return getPreferencesHelper().getCountry()!!
    }

    fun getCategory(): String {
        return getPreferencesHelper().getCategory()!!
    }

    fun setCountry(value: String) {
        _viewState.value = HomeViewState.ChangedCountry(value)
        getPreferencesHelper().setCountry(value)
        loadData()
    }

    fun setCategory(category: String) {
        getPreferencesHelper().setCategory(category)
        loadData()
    }

    fun loadData() {
        _isLoading.postValue(true)
        val param = HashMap<String?, String?>()
        param.put("country", getPreferencesHelper().getCountry())
        param.put("category", getPreferencesHelper().getCategory())
        param.put("apiKey", API_KEY)
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
                    _isLoading.postValue(false)
                    _viewState.value = HomeViewState.Success(response)
                }

                override fun onError(err: Throwable) {
                    _isLoading.postValue(false)
                    _viewState.value = HomeViewState.Error(err.toString())
                }
            })
    }
}

sealed class HomeViewState {
    data class Error(val message: String) : HomeViewState()

    data class Success(val response: NewsResponse) : HomeViewState()

    data class ChangedCountry(val country: String) : HomeViewState()
}