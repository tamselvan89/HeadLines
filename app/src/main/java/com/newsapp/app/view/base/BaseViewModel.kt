package com.newsapp.app.view.base

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import com.newsapp.app.data.prefs.PreferencesHelper
import com.newsapp.app.data.rest.ApiService
import com.newsapp.app.utils.rx.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel constructor(
    private val mCompositeDisposable: CompositeDisposable? = CompositeDisposable(),
    private val baseSchedulerProvider: BaseSchedulerProvider,
    private val preferencesHelper: PreferencesHelper,
    private val apiService: ApiService,
    application: Application
) : AndroidViewModel(application) {

    private val mIsLoading = ObservableBoolean()


    override fun onCleared() {
        mCompositeDisposable?.dispose()
        super.onCleared()
    }

    fun getCompositeDisposal(): CompositeDisposable? {
        return mCompositeDisposable
    }

    fun getBaseSchedulerProvider(): BaseSchedulerProvider? {
        return baseSchedulerProvider
    }

    fun getPreferencesHelper(): PreferencesHelper {
        return preferencesHelper
    }

    fun getApiService(): ApiService {
        return apiService
    }

    fun getIsLoading(): ObservableBoolean {
        return mIsLoading
    }

}