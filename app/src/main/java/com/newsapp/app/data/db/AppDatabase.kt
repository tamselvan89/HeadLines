package com.newsapp.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.newsapp.app.data.db.dao.ArticleDao
import com.newsapp.app.data.db.entitiy.EnArticle

@Database(entities = [EnArticle::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao?
}