package com.newsapp.app.view.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment

private val TAG = BaseFragment::class.java.simpleName

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : DaggerFragment() {

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
    private lateinit var mRootView: View
    private lateinit var mActivity: BaseActivity<*, *>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity: BaseActivity<*, *> = context
            mActivity = activity
            activity.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutRes(), container, false)
        mRootView = mViewDataBinding.root
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()
    }

    open fun getBaseActivity(): BaseActivity<*, *> {
        return mActivity
    }

    open fun getViewDataBinding(): T {
        return mViewDataBinding
    }

    open fun hideKeyboard() {
        mActivity.hideKeyboard()
    }

    open fun isNetworkConnected(): Boolean {
        return mActivity.isNetworkConnected()
    }

    open fun showLoading() {
        mActivity.showLoading()
    }

    open fun hideLoading() {
        mActivity.hideLoading()
    }

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String?)
    }
}