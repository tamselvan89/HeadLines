package com.newsapp.app.generic

import com.newsapp.app.BuildConfig


object AppConstants {

    //TODO Release build - Should be false
    const val DEVELOPMENT_MODE = true

    const val LOG_ENABLED = true

    const val APP_VERSION = "V" + BuildConfig.VERSION_NAME

    const val APP_NAME = "MVVMTemplate"
    const val DB_NAME = "mvvmTemplate.db"
    const val PREF_NAME = "mvvmTemplate_pref"

    const val TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss"

    const val NULL_INDEX = -1L

    // Intent request codes
    const val REQUEST_CODE_UPDATE = 100
}