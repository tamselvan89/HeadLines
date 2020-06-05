package com.newsapp.app.utils


import android.util.Log
import com.newsapp.app.BuildConfig

object Logger {

    fun v(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, msg)
        }
    }

    fun v(tag: String, msg: String, throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, msg, throwable)
        }
    }

    fun d(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg)
        }
    }

    fun d(tag: String, msg: String, throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg, throwable)
        }
    }

    fun i(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg)
        }
    }

    fun i(tag: String, msg: String, throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg, throwable)
        }
    }

    fun w(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, msg)
        }
    }

    fun w(tag: String, msg: String, throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, msg, throwable)
        }
    }

    fun e(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg)
        }
    }

    fun e(tag: String, msg: String, throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg, throwable)
        }
    }

    fun request(tag: String, url: String, request: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, String.format("Request url: %s\n    payload:%s\n", url, request))
        }
    }

    fun response(tag: String, request: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, String.format("Response : %s\n", request))
        }
    }
}
