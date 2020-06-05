package com.newsapp.app.di.module

import android.content.Context
import androidx.room.Room
import com.newsapp.app.data.db.AppDatabase
import com.newsapp.app.data.db.DbHelper
import com.newsapp.app.data.db.dao.AppDbHelper
import com.newsapp.app.data.prefs.AppPreferencesHelper
import com.newsapp.app.data.prefs.PreferencesHelper
import com.newsapp.app.data.rest.ApiService
import com.newsapp.app.di.scope.DatabaseInfo
import com.newsapp.app.di.scope.PreferenceInfo
import com.newsapp.app.generic.AppConstants
import com.newsapp.app.utils.rx.BaseSchedulerProvider
import com.newsapp.app.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class ApplicationModule {

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    internal fun provideSchedulerProvider(): BaseSchedulerProvider =
        SchedulerProvider()

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String? {
        return AppConstants.PREF_NAME
    }

    @Provides
    @Singleton
    fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): PreferencesHelper {
        return appPreferencesHelper
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@DatabaseInfo dbName: String?, context: Context?): AppDatabase? {
        return Room.databaseBuilder(
            context?.applicationContext!!,
            AppDatabase::class.java,
            dbName!!
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String? {
        return AppConstants.DB_NAME
    }

    @Provides
    @Singleton
    fun provideDbHelper(appDbHelper: AppDbHelper?): DbHelper? {
        return appDbHelper
    }
}