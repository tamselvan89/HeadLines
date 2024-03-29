package com.newsapp.app.generic

import com.newsapp.app.BuildConfig


object AppConstants {

    //TODO Release build - Should be false
    const val DEVELOPMENT_MODE = true

    const val LOG_ENABLED = true

    const val APP_VERSION = "V" + BuildConfig.VERSION_NAME

    const val PREF_NAME = "newsapp_pref"

    const val TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss"

    const val NULL_INDEX = -1L

    const val SPLASH_TIME_OUT = 3000L

    const val API_KEY = "fdb429f4a1eb4aeb93110a6f1f05167c"
}