package com.newsapp.app.view.base

import android.annotation.TargetApi
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import butterknife.ButterKnife
import com.google.android.material.snackbar.Snackbar
import com.newsapp.app.BuildConfig
import com.newsapp.app.utils.CommonUtil
import com.newsapp.app.utils.NetworkUtils
import com.newsapp.app.R
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

private val TAG = BaseActivity::class.java.simpleName

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : DaggerAppCompatActivity(),
    BaseFragment.Callback {

    /** @return layout resource id*/
    @LayoutRes
    protected abstract fun layoutRes(): Int

    /** Override for set binding variable
     * @return variable id*/
    protected abstract fun getBindingVariable(): Int

    /** Override for set view model
     * @return view model instance*/
    abstract fun getViewModel(): V
    protected abstract fun setUp()

    private lateinit var mViewDataBinding: T
    private lateinit var mViewModel: V

    private var mProgressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun initialize() {
        performDataBinding()
        ButterKnife.bind(this)
        setUp()
    }

    open fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, layoutRes())
        this.mViewModel = getViewModel()
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()
        mViewDataBinding.lifecycleOwner = this
    }

    open fun getViewDataBinding(): T {
        return mViewDataBinding
    }

    @TargetApi(Build.VERSION_CODES.M)
    open fun requestPermissionsSafely(permissions: Array<String?>?, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions!!, requestCode)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    open fun hasPermission(permission: String?): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission!!) == PackageManager.PERMISSION_GRANTED
    }

    open fun openAppSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + BuildConfig.APPLICATION_ID)
        startActivity(intent)
    }

    open fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkConnected(baseContext)
    }

    open fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    open fun showLoading() {
        hideLoading()
        mProgressDialog = CommonUtil.showProgressDialog(
            this, resources.getString(R.string.txt_progress_loading)
        )
    }

    open fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            CommonUtil.dismissProgressDialog()
        }
    }

    open fun onError(message: String?) {
        if (message != null) {
            ShowSnackBarMessage(message)
        }
    }

    open fun showMessage(message: String?) {
        if (message != null) {
            CommonUtil.ShowLongToast(this, message)
        }
    }

    open fun ShowSnackBarPermissionMessage(message: Int) {
        val snackbar: Snackbar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackbar.view
        snackbar.setAction(getString(R.string.snack_bar_settings)) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:" + BuildConfig.APPLICATION_ID)
            startActivity(intent)
        }
        val textView =
            snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        TextViewCompat.setTextAppearance(textView, R.style.SnackBarTextStyle)
        textView.setTextColor(Color.WHITE)
        textView.isSingleLine = false
        snackbar.show()
    }

    open fun ShowSnackBarMessage(message: Int) {
        val snackbar: Snackbar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackbar.view
        val textView =
            snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        TextViewCompat.setTextAppearance(textView, R.style.SnackBarTextStyle)
        textView.setTextColor(Color.WHITE)
        textView.isSingleLine = false
        snackbar.show()
    }

    open fun ShowSnackBarMessage(message: String) {
        val snackbar: Snackbar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackbar.view
        val textView =
            snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        TextViewCompat.setTextAppearance(textView, R.style.SnackBarTextStyle)
        textView.setTextColor(Color.WHITE)
        textView.isSingleLine = false
        snackbar.show()
    }

    fun ShowSnackBarErrMsg(msg: String) {
        val snackbar: Snackbar =
            Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
        val snackBarView = snackbar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorErr))
        val textView =
            snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        TextViewCompat.setTextAppearance(textView, R.style.SnackBarTextStyle)
        textView.setTextColor(Color.WHITE)
        textView.isSingleLine = false
        snackbar.show()
        snackbar.show()
        snackbar.show()
    }

}