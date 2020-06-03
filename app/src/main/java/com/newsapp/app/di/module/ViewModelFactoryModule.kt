package com.newsapp.app.di.module

import androidx.lifecycle.ViewModelProvider
import com.newsapp.app.utils.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}