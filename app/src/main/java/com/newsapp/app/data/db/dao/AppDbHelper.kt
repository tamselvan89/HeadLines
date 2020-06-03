package com.newsapp.app.data.db.dao

import com.newsapp.app.data.db.AppDatabase
import com.newsapp.app.data.db.DbHelper
import com.newsapp.app.data.db.entitiy.EnArticle
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDbHelper @Inject constructor(appDatabase: AppDatabase) : DbHelper {

    val appDatabase: AppDatabase = appDatabase

    override fun insertArticle(article: EnArticle): Observable<Boolean> {
        return Observable.fromCallable {
            appDatabase.articleDao()?.insertArticle(article)
            true
        }
    }

    override fun insertAllArticle(articleList: List<EnArticle>): Observable<Boolean> {
        return Observable.fromCallable {
            appDatabase.articleDao()?.insertAllArticle(articleList)
            true
        }
    }

    override fun deleteAllArticle(): Observable<Boolean> {
        return Observable.fromCallable {
            appDatabase.articleDao()?.deleteAllArticle()
            true
        }
    }

    override fun getAllArticles(): Observable<List<EnArticle>> {
        return appDatabase.articleDao()?.getAllArticle()?.toObservable()!!
    }
}