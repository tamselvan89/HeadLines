package com.newsapp.app.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.newsapp.app.BuildConfig
import com.newsapp.app.di.scope.PreferenceInfo
import com.newsapp.app.utils.Cryptography
import javax.inject.Inject

class AppPreferencesHelper @Inject constructor(
    context: Context,
    @PreferenceInfo prefFileName: String
) : PreferencesHelper {

    private val PREF_KEY_COUNTRY = "PREF_KEY_COUNTRY"
    private val PREF_KEY_CATEGORY = "PREF_KEY_CATEGORY"

    private var mPrefs: SharedPreferences =
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun getCountry(): String? {
        return getStringFromEditor(PREF_KEY_COUNTRY)
    }

    override fun setCountry(accessToken: String) {
        putStringToEditor(PREF_KEY_COUNTRY, accessToken)
    }

    override fun getCategory(): String? {
        return getStringFromEditor(PREF_KEY_CATEGORY)
    }

    override fun setCategory(email: String) {
        putStringToEditor(PREF_KEY_CATEGORY, email)
    }

    /*Encode the value*/
    private fun putStringToEditor(key: String, value: String) {
        if (BuildConfig.DEBUG) {
            mPrefs.edit().putString(key, value).apply()
        } else {
            mPrefs.edit().putString(Cryptography.encrypt(key), Cryptography.encrypt(value))
                .apply()
        }
    }

    /*Decode the value*/
    private fun getStringFromEditor(key: String): String? {
        if (BuildConfig.DEBUG) {
            return mPrefs.getString(key, null)
        } else {
            val encrypted = mPrefs.getString(Cryptography.encrypt(key), "")
            return Cryptography.decrypt(encrypted)
        }
    }
}