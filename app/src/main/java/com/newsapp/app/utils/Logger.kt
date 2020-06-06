package com.newsapp.app.utils


import android.util.Log
import com.newsapp.app.generic.AppConstants.LOG_ENABLED

object Logger {

    fun v(tag: String, msg: String) {
        if (LOG_ENABLED) {
            Log.v(tag, msg)
        }
    }

    fun v(tag: String, msg: String, throwable: Throwable) {
        if (LOG_ENABLED) {
            Log.v(tag, msg, throwable)
        }
    }

    fun d(tag: String, msg: String) {
        if (LOG_ENABLED) {
            Log.d(tag, msg)
        }
    }

    fun d(tag: String, msg: String, throwable: Throwable) {
        if (LOG_ENABLED) {
            Log.d(tag, msg, throwable)
        }
    }

    fun i(tag: String, msg: String) {
        if (LOG_ENABLED) {
            Log.i(tag, msg)
        }
    }

    fun i(tag: String, msg: String, throwable: Throwable) {
        if (LOG_ENABLED) {
            Log.i(tag, msg, throwable)
        }
    }

    fun w(tag: String, msg: String) {
        if (LOG_ENABLED) {
            Log.w(tag, msg)
        }
    }

    fun w(tag: String, msg: String, throwable: Throwable) {
        if (LOG_ENABLED) {
            Log.w(tag, msg, throwable)
        }
    }

    fun e(tag: String, msg: String) {
        if (LOG_ENABLED) {
            Log.e(tag, msg)
        }
    }

    fun e(tag: String, msg: String, throwable: Throwable) {
        if (LOG_ENABLED) {
            Log.e(tag, msg, throwable)
        }
    }

    fun request(tag: String, url: String, request: String) {
        if (LOG_ENABLED) {
            Log.i(tag, String.format("Request url: %s\n    payload:%s\n", url, request))
        }
    }

    fun response(tag: String, request: String) {
        if (LOG_ENABLED) {
            Log.i(tag, String.format("Response : %s\n", request))
        }
    }
}
