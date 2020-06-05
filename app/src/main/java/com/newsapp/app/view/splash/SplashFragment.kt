package com.newsapp.app.view.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.newsapp.app.R
import com.newsapp.app.generic.AppConstants.SPLASH_TIME_OUT
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment(), CoroutineScope {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch {
            delay(SPLASH_TIME_OUT)
            withContext(Dispatchers.Main) {
                val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()
}
