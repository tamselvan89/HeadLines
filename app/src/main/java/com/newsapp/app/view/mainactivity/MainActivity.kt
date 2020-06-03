package com.newsapp.app.view.mainactivity

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.newsapp.app.BaseApplication
import com.newsapp.app.R
import com.newsapp.app.BR
import com.newsapp.app.data.model.NewsResponse
import com.newsapp.app.databinding.ActivityMainBinding
import com.newsapp.app.extension.observeLiveData
import com.newsapp.app.utils.CommonUtil
import com.newsapp.app.utils.ViewModelProviderFactory
import com.newsapp.app.view.base.BaseActivity
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

private val TAG = MainActivity::class.java.simpleName

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var newsResponse: NewsResponse

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var factory: ViewModelProviderFactory

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): MainViewModel {
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        return mainViewModel
    }

    override fun setUp() {
        ShowSnackBarErrMsg(getNativeOutput())
        setObservers(mainViewModel)

        CommonUtil.showProgressDialog(this, "")
        mainViewModel.loadData()

    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String?) {

    }

    private fun setObservers(mainViewModel: MainViewModel) {
        observeLiveData(mainViewModel.getNewsResponse()) {
            CommonUtil.dismissProgressDialog()
            newsResponse = it
        }
    }

    fun getNativeOutput(): String {
        return BaseApplication().Hello()
    }

    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.txt_value -> {
                newsResponse.articles?.get(0)?.title?.let { ShowSnackBarErrMsg(it) }
            }
        }
    }
}
