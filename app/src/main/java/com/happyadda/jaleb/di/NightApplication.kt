package com.happyadda.jaleb.di

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.happyadda.jaleb.R
import com.happyadda.jaleb.interfaces.NightDayLightSunService
import com.happyadda.jaleb.utils.comhappyaddajaleb
import com.onesignal.OneSignal
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class NightApplication : Application() {

    lateinit var nightDayLightSunService: NightDayLightSunService
    override fun onCreate() {
        super.onCreate()
        AppsFlyerLib.getInstance()
            .init(resources.getString(R.string.apps_dev_key), nigListener, this)
        AppsFlyerLib.getInstance().start(this)

        OneSignal.initWithContext(this)
        OneSignal.setAppId(resources.getString(R.string.one_signal_key))
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        val nightDayLightSunServiceRetro = Retrofit.Builder()
            .baseUrl("jhfws://vxqt.jltqumytgfovnitlt.frm/loyrpt263/".comhappyaddajaleb())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        nightDayLightSunService =
            nightDayLightSunServiceRetro.create(NightDayLightSunService::class.java)
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