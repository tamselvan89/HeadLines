package com.newsapp.app.view.mainactivity

import android.os.Bundle
import com.newsapp.app.R
import dagger.android.support.DaggerAppCompatActivity

private val TAG = MainActivity::class.java.simpleName

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
