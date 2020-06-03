package com.newsapp.app.view.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentManager
import dagger.android.support.DaggerDialogFragment

abstract class BaseDialog : DaggerDialogFragment() {

    lateinit var mActivity: BaseActivity<*, *>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val mActivity: BaseActivity<*, *> = context
            this.mActivity = mActivity
            mActivity.onFragmentAttached()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // the content
        val root = RelativeLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        // creating the fullscreen dialog
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        if (dialog.window != null) {
            dialog.window!!
                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun show(
        fragmentManager: FragmentManager,
        tag: String?
    ) {
        val transaction =
            fragmentManager.beginTransaction()
        val prevFragment = fragmentManager.findFragmentByTag(tag)
        if (prevFragment != null) {
            transaction.remove(prevFragment)
        }
        transaction.addToBackStack(null)
        show(transaction, tag)
    }

    open fun dismissDialog(tag: String?) {
        dismiss()
        getBaseActivity().onFragmentDetached(tag)
    }

    open fun getBaseActivity(): BaseActivity<*, *> {
        return mActivity
    }

    open fun hideKeyboard() {
        mActivity.hideKeyboard()
    }

    open fun hideLoading() {
        mActivity.hideLoading()
    }

    open fun isNetworkConnected(): Boolean {
        return mActivity.isNetworkConnected()
    }

    open fun showLoading() {
        mActivity.showLoading()
    }
}