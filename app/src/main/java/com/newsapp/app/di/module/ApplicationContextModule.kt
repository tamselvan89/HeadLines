package com.newsapp.app.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ApplicationContextModule {

    @Binds
    @Singleton
    abstract fun provideApplicationContext(application: Application): Context
}