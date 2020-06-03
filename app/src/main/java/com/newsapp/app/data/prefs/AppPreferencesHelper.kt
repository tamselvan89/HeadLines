package com.newsapp.app.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.newsapp.app.BuildConfig
import com.newsapp.app.di.scope.PreferenceInfo
import com.newsapp.app.generic.AppConstants
import com.newsapp.app.utils.Cryptography
import javax.inject.Inject

class AppPreferencesHelper @Inject constructor(
    context: Context,
    @PreferenceInfo prefFileName: String?
) : PreferencesHelper {

    private val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
    private val PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL"
    private val PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID"
    private val PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME"
    private val PREF_KEY_CURRENT_USER_PROFILE_PIC_URL = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL"

    private var mPrefs: SharedPreferences? =
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun getAccessToken(): String? {
        return getStringFromEditor(PREF_KEY_ACCESS_TOKEN)
    }

    override fun setAccessToken(accessToken: String?) {
        putStringToEditor(PREF_KEY_ACCESS_TOKEN, accessToken)
    }

    override fun getCurrentUserEmail(): String? {
        return getStringFromEditor(PREF_KEY_CURRENT_USER_EMAIL)
    }

    override fun setCurrentUserEmail(email: String?) {
        putStringToEditor(PREF_KEY_CURRENT_USER_EMAIL, email)
    }

    override fun getCurrentUserId(): Long? {
        val userId = getStringFromEditor(PREF_KEY_CURRENT_USER_ID)
        return if (userId == null) AppConstants.NULL_INDEX else return userId.toLong()
    }

    override fun setCurrentUserId(userId: Long?) {
        val id = userId ?: AppConstants.NULL_INDEX
        putStringToEditor(PREF_KEY_CURRENT_USER_ID, java.lang.String.valueOf(id))
    }

    override fun getCurrentUserName(): String? {
        return getStringFromEditor(PREF_KEY_CURRENT_USER_NAME)
    }

    override fun setCurrentUserName(userName: String?) {
        putStringToEditor(PREF_KEY_CURRENT_USER_NAME, userName)
    }

    override fun getCurrentUserProfilePicUrl(): String? {
        return getStringFromEditor(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL)
    }

    override fun setCurrentUserProfilePicUrl(profilePicUrl: String?) {
        putStringToEditor(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, profilePicUrl)
    }

    /*Encode the value*/
    private fun putStringToEditor(key: String, value: String?) {
        if (BuildConfig.DEBUG) {
            mPrefs!!.edit().putString(key, value).apply()
        } else {
            mPrefs!!.edit().putString(Cryptography.encrypt(key), Cryptography.encrypt(value))
                .apply()
        }
    }

    /*Decode the value*/
    private fun getStringFromEditor(key: String): String? {
        if (BuildConfig.DEBUG) {
            return mPrefs!!.getString(key, null)
        } else {
            val encrypted = mPrefs!!.getString(Cryptography.encrypt(key), null)
            return Cryptography.decrypt(encrypted)
        }
    }
}