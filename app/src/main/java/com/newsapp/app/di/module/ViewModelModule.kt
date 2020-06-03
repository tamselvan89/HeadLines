package com.newsapp.app.di.module

import androidx.lifecycle.ViewModel
import com.newsapp.app.di.scope.ViewModelKey
import com.newsapp.app.view.mainactivity.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainActivity(mainViewModel: MainViewModel): ViewModel

}