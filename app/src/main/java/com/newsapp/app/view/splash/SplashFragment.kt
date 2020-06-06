package com.newsapp.app.view.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.newsapp.app.BR
import com.newsapp.app.R
import com.newsapp.app.databinding.FragmentSplashBinding
import com.newsapp.app.generic.AppConstants.SPLASH_TIME_OUT
import com.newsapp.app.utils.ViewModelProviderFactory
import com.newsapp.app.view.base.BaseFragment
import com.newsapp.app.view.home.HomeViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : BaseFragment<FragmentSplashBinding, HomeViewModel>(), CoroutineScope {

    private lateinit var homeViewModel: HomeViewModel

    @Inject
    internal lateinit var factory: ViewModelProviderFactory

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    override fun layoutRes(): Int {
        return R.layout.fragment_splash
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): HomeViewModel {
        homeViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        return homeViewModel
    }

    override fun setUp() {
        homeViewModel.setInitialValue()
        launch {
            delay(SPLASH_TIME_OUT)
            withContext(Dispatchers.Default) {
                val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }
    }
}
