package com.newsapp.app.di.module

import com.newsapp.app.di.scope.ActivityScope
import com.newsapp.app.view.mainactivity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    /**
     * Automatically create sub-component with module
     *
     * @return The {@linkplain MainActivity}
     */
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}