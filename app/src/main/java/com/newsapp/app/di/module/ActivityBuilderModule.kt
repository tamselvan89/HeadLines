package com.newsapp.app.di.module

import com.newsapp.app.di.scope.ActivityScope
import com.newsapp.app.view.home.CountryDialog
import com.newsapp.app.view.home.HomeFragment
import com.newsapp.app.view.mainactivity.MainActivity
import com.newsapp.app.view.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashFragment

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeHomeFrgment(): HomeFragment

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeCountryDialog(): CountryDialog
}