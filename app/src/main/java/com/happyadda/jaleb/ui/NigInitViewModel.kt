package com.happyadda.jaleb.ui

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.happyadda.jaleb.data.web.model.NigLink
import com.happyadda.jaleb.data.web.repo.NigLighGistightSigssaImpl
import com.happyadda.jaleb.interfaces.NightDayLightSunService
import com.happyadda.jaleb.utils.comhappyaddajaleb
import com.onesignal.OneSignal
import kotlinx.coroutines.launch

class NigInitViewModel : ViewModel() {
    private val nigMainLink = NigLink()

    fun nigCollectLink(context: Context): String {
        return nigMainLink.nigCollect(context)
    }

    fun setUrlAndOrganic(service: NightDayLightSunService, callback: (Boolean) -> Unit) =
        viewModelScope.launch {

            NigLighGistightSigssaImpl(service).nigLighGistiUrlAndSwitchghtSigssaImpl { url, switch ->
                nigMainLink.nigOrganicAccess = switch
                nigMainLink.nigUrl = url
                callback(url.contains("jhfw".comhappyaddajaleb()))
            }
        }

    fun nigSetDeepLink(uri: Uri?) {
        nigMainLink.nigDeepLink = uri?.toString()
        nigMainLink.nigDeepLink?.let {
            val nigArrayDeepLink = it.split("//")
            nigMainLink.nigSubAll = nigArrayDeepLink[1].split("_")
        }
    }

    fun nigSetAfUserId(id: String) {
        nigMainLink.nigAppsFlyerUserId = id
    }

    fun nigSetGoogleID(id: String) {
        nigMainLink.nigGoogleId = id
        OneSignal.setExternalUserId(id)
    }

    fun nigSetAfStatus(value: String) {
        val nigOrganic = "qfshnxr".comhappyaddajaleb().replaceFirstChar { it.uppercase() }
        if (value == nigOrganic && nigMainLink.nigDeepLink == null) {
            nigMainLink.nigMediaSource = "qfshnxr".comhappyaddajaleb()
        }

    }

    fun nigSetMediaSource(value: String) {
        nigMainLink.nigMediaSource = value
    }

    fun nigSetCampaign(value: String) {
        nigMainLink.nigCampaign = value
        nigMainLink.nigCampaign?.let {
            nigMainLink.nigSubAll = it.split("_")
        }
    }

    fun nigSetAfChannel(value: String) {
        nigMainLink.nigAfChannel = value
    }

    fun checkForOrganic(): Boolean {
        return nigMainLink.nigMediaSource == "qfshnxr".comhappyaddajaleb() && nigMainLink.nigOrganicAccess == false
    }
}