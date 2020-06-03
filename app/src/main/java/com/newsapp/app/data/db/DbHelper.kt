package com.newsapp.app.data.db

import com.newsapp.app.data.db.entitiy.EnArticle
import io.reactivex.Observable

interface DbHelper {

    fun insertArticle(article: EnArticle): Observable<Boolean>

    fun insertAllArticle(articleList: List<EnArticle>): Observable<Boolean>

    fun deleteAllArticle(): Observable<Boolean>

    fun getAllArticles(): Observable<List<EnArticle>>
}