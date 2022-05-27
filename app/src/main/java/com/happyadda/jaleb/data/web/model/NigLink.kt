package com.happyadda.jaleb.data.web.model

import android.content.Context
import com.happyadda.jaleb.R
import com.happyadda.jaleb.utils.comhappyaddajaleb


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

        val nigStrMediaSource = "?osppa_hdsrfh=".comhappyaddajaleb()
        val nigStrGoogleId = "&icanlt_pbig=".comhappyaddajaleb()
        val nigStrAppsFlyerUserId = "&ct_gzegxb=".comhappyaddajaleb()
        val nigStrPackageName = "&dizklt=".comhappyaddajaleb()
        val nigStrAppsFlyerDevKey = "&fsh_ren=".comhappyaddajaleb()
        val nigStrFbToken = "&hp_ma=".comhappyaddajaleb()
        val nigStrFbAppId = "&hp_mwp_xs=".comhappyaddajaleb()
        val nigStrAfChannel = "&ct_ooacccl=".comhappyaddajaleb()
        val nigStrCampaign = "&eoywaxvl=".comhappyaddajaleb()

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












