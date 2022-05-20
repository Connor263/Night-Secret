package com.happyadda.jaleb.ui

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.happyadda.jaleb.data.web.model.NigLink
import com.happyadda.jaleb.utils.vigenere
import com.onesignal.OneSignal

class NigInitViewModel : ViewModel() {
    private val nigMainLink = NigLink()

    fun nigCollectLink(context: Context): String {
        return nigMainLink.nigCollect(context)
    }

    fun setUrlAndOrganic(url: String, organic: Boolean, callback: (Boolean) -> Unit) {
        nigMainLink.nigOrganicAccess = organic
        nigMainLink.nigUrl = url
        callback(url.contains("jhfw".vigenere()))
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
        val nigOrganic = "qfshnxr".vigenere().replaceFirstChar { it.uppercase() }
        if (value == nigOrganic && nigMainLink.nigDeepLink == null) {
            nigMainLink.nigMediaSource = "qfshnxr".vigenere()
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
        return nigMainLink.nigMediaSource == "qfshnxr".vigenere() && nigMainLink.nigOrganicAccess == false
    }
}