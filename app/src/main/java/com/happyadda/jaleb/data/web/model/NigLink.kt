package com.happyadda.jaleb.data.web.model

import android.content.Context
import com.happyadda.jaleb.R
import com.happyadda.jaleb.utils.vigenere


data class NigLink(
    var nigGoogleId: String? = null,
    var nigAppsFlyerUserId: String? = null,
    var nigSubAll: List<String> = listOf("", "", "", "", "", "", "", "", "", ""),
    var nigDeepLink: String? = null,
    var nigMediaSource: String? = null,
    var nigAfChannel: String? = null,
    var nigCampaign: String? = null,
    var nigUrl: String? = null,
    var nigOrganicAccess: Boolean? = null
) {
    fun nigCollect(context: Context): String {
        val nigResources = context.resources
        val nigPackageName = context.packageName
        val nigAppsFlyerDevKey = nigResources.getString(R.string.apps_dev_key)
        val nigFbToken = nigResources.getString(R.string.fb_at)
        val nigFbAppId = nigResources.getString(R.string.fb_app_id)

        var nigIndex = 0
        val nigSubsString = nigSubAll.joinToString(separator = "") {
            nigIndex++
            "&sub$nigIndex=$it"
        }

        val nigStrMediaSource = "?osppa_hdsrfh=".vigenere()
        val nigStrGoogleId = "&icanlt_pbig=".vigenere()
        val nigStrAppsFlyerUserId = "&ct_gzegxb=".vigenere()
        val nigStrPackageName = "&dizklt=".vigenere()
        val nigStrAppsFlyerDevKey = "&fsh_ren=".vigenere()
        val nigStrFbToken = "&hp_ma=".vigenere()
        val nigStrFbAppId = "&hp_mwp_xs=".vigenere()
        val nigStrAfChannel = "&ct_ooacccl=".vigenere()
        val nigStrCampaign = "&eoywaxvl=".vigenere()

        return "${this.nigUrl}" +
                "$nigStrMediaSource${this.nigMediaSource}" +
                "$nigStrGoogleId${this.nigGoogleId}" +
                "$nigStrAppsFlyerUserId${this.nigAppsFlyerUserId}" +
                "$nigStrPackageName$nigPackageName" +
                "$nigStrAppsFlyerDevKey$nigAppsFlyerDevKey" +
                "$nigStrFbToken$nigFbToken" +
                "$nigStrFbAppId$nigFbAppId" +
                "$nigStrAfChannel${this.nigAfChannel}" +
                "$nigStrCampaign${this.nigCampaign}" +
                nigSubsString
    }
}












