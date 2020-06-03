package com.newsapp.app.di.component

import android.app.Application
import com.newsapp.app.BaseApplication
import com.newsapp.app.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ActivityBuilderModule::class,
        ApplicationContextModule::class,
        ApplicationModule::class,
        ViewModelModule::class,
        ViewModelFactoryModule::class,
        AndroidSupportInjectionModule::class]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    override fun inject(baseApplication: BaseApplication)

    @Component.Builder
    interface Builder {
        /**
         * [BindsInstance] annotation is used for, if you want to bind particular object or instance
         * of an object through the time of component construction
         */
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

}