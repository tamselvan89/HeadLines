package com.newsapp.app.data.prefs

interface PreferencesHelper {

    fun getCountry(): String?

    fun setCountry(accessToken: String)

    fun getCategory(): String?

    fun setCategory(email: String)
    }