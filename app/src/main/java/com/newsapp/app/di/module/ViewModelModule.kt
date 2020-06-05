package com.newsapp.app.di.module

import androidx.lifecycle.ViewModel
import com.newsapp.app.di.scope.ViewModelKey
import com.newsapp.app.view.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeFragment(homeViewModel: HomeViewModel): ViewModel

}