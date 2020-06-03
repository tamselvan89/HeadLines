package com.newsapp.app.data.prefs

interface PreferencesHelper {

    fun getAccessToken(): String?

    fun setAccessToken(accessToken: String?)

    fun getCurrentUserEmail(): String?

    fun setCurrentUserEmail(email: String?)

    fun getCurrentUserId(): Long?

    fun setCurrentUserId(userId: Long?)

    fun getCurrentUserName(): String?

    fun setCurrentUserName(userName: String?)

    fun getCurrentUserProfilePicUrl(): String?

    fun setCurrentUserProfilePicUrl(profilePicUrl: String?)
}