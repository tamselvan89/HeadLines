package com.newsapp.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newsapp.app.data.db.entitiy.EnArticle
import io.reactivex.Single

@Dao
interface ArticleDao {

    @Query("DELETE FROM Article")
    fun deleteAllArticle()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(enArticle: EnArticle)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllArticle(articleList: List<EnArticle>)

    @Query("SELECT * FROM Article")
    fun getAllArticle(): Single<List<EnArticle>>

}