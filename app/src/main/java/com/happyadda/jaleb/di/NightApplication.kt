package com.happyadda.jaleb.di

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.happyadda.jaleb.R
import com.onesignal.OneSignal

class NightApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppsFlyerLib.getInstance()
            .init(resources.getString(R.string.apps_dev_key), nigListener, this)
        AppsFlyerLib.getInstance().start(this)

        OneSignal.initWithContext(this)
        OneSignal.setAppId(resources.getString(R.string.one_signal_key))
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
    }

    private val nigListener = object : AppsFlyerConversionListener {
        override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
            data?.let {
                nigAppsFlyerState.postValue(it)
            }
        }

        override fun onConversionDataFail(p0: String?) {}

        override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}

        override fun onAttributionFailure(p0: String?) {}

    }

    companion object {
        val nigAppsFlyerState = MutableLiveData<MutableMap<String, Any>?>(null)
    }
}