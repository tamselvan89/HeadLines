package com.newsapp.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.multidex.MultiDex
import com.newsapp.app.data.enums.ActivityState
import com.newsapp.app.di.component.AppComponent
import com.newsapp.app.di.component.DaggerAppComponent
import com.newsapp.app.utils.Logger
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

private val TAG = BaseApplication::class.java.simpleName

class BaseApplication : DaggerApplication(), Application.ActivityLifecycleCallbacks,
    HasAndroidInjector {

    private val activities = ArrayList<Activity>()
    private val activityStates = HashMap<Activity, ActivityState>()

    companion object {
        lateinit var baseContext: Context
    }

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        Logger.d(TAG, "Application Created")

        BaseApplication.baseContext = applicationContext
        registerActivityLifecycleCallbacks(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector


    override fun onActivityPaused(activity: Activity) {
        Logger.i(TAG, "OnActivityPaused")
        activityStates[activity] = ActivityState.PAUSED
    }

    override fun onActivityStarted(activity: Activity) {
        val appWasInBackground = appIsInBackground()
        activityStates[activity] = ActivityState.STARTED
        if (appWasInBackground) {
            /*Logger.e(TAG, "appWasInBackground")
            if (!AppHelper.isMyServiceRunning(GPSService::class.java)) {
                Logger.d(TAG, "Start Service")
                startService(Intent(this, GPSService::class.java))
            }
            if (!EventBus.getDefault().isRegistered(this)) {
                mediaPlayer = reAssignPlayer(mediaPlayer)
                EventBus.getDefault().register(this)
            }*/
        }
    }

    private fun appIsInBackground(): Boolean {
        for (state in activityStates.values) {
            if (!(state == ActivityState.STOPPED || state == ActivityState.CREATED)) {
                return false
            }
        }
        return true
    }

    override fun onActivityDestroyed(activity: Activity) {
        Logger.e(TAG, "An activity was destroyed: $activity. Activities: $activities")
        activities.remove(activity)
        activityStates.remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityStopped(activity: Activity) {
        activityStates[activity] = ActivityState.STOPPED
        if (appIsInBackground()) {
            Logger.e(TAG, "AppIsInBackground")
            /*if (AppHelper.isMyServiceRunning(GPSService::class.java)) {
                Logger.d(TAG, "Stop Service")
                stopService(Intent(this, GPSService::class.java))
            }*/
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Logger.i(TAG, "An activity was created: $activity. Activities: $activities")
        activities.add(activity)
        activityStates[activity] = ActivityState.CREATED
    }

    override fun onActivityResumed(activity: Activity) {
        Logger.e(TAG, "OnActivityResumed")
        activityStates[activity] = ActivityState.RESUMED
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication?>? {
        val component: AppComponent = DaggerAppComponent.builder().application(this).build()
        component.inject(this)
        return component
    }
}