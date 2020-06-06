package com.newsapp.app.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.provider.Settings
import android.util.Patterns
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.newsapp.app.R
import com.newsapp.app.generic.AppConstants
import java.io.IOException
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*

object CommonUtil {

    private var progressDialog: Dialog? = null

    @SuppressLint("all")
    fun getDeviceId(context: Context): String? {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    fun getTimestamp(): String? {
        return SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.US)
            .format(Date())
    }

    fun isEmailValid(email: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    @Throws(IOException::class)
    fun loadJSONFromAsset(context: Context, jsonFileName: String?): String? {
        val manager = context.assets
        val `is` = manager.open(jsonFileName!!)
        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        return String(buffer, Charset.forName("UTF-8"))
    }

    fun showProgressDialog(ctx: Context, text: String): Dialog? {
        try {
            progressDialog = Dialog(ctx, R.style.Dialog)
            val inflater = (ctx as Activity).layoutInflater
            val convertview: View = inflater.inflate(R.layout.dialog_progress_bar, null)
            val textViewProgress = convertview.findViewById<TextView>(R.id.progresstext)
            textViewProgress.visibility = View.VISIBLE
            if (text.isEmpty()) textViewProgress.visibility = View.GONE
            textViewProgress.text = text
            progressDialog!!.setContentView(convertview)
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return CommonUtil.progressDialog
    }

    fun dismissProgressDialog() {
        try {
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun ShowLongToast(ctx: Context?, msg: String?) {
        val popUpToast = Toast.makeText(ctx, msg, Toast.LENGTH_LONG)
        popUpToast.show()
    }

    fun ShowToast(ctx: Context?, msg: String?) {
        val popUpToast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT)
        popUpToast.show()
    }

    fun getJsonFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString =
                context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}