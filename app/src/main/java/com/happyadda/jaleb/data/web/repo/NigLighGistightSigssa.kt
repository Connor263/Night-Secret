package com.happyadda.jaleb.data.web.repo

import com.happyadda.jaleb.interfaces.NigLighGistightSigssa
import com.happyadda.jaleb.interfaces.NightDayLightSunService

class NigLighGistightSigssaImpl(private val service: NightDayLightSunService) : NigLighGistightSigssa {
    override suspend fun nigLighGistiUrlAndSwitchghtSigssaImpl(callback: (String, Boolean) -> Unit) {
        val nnigLighGistightSigssaImplresult = service.nightDayLightSunServiceOkNightService()
        nnigLighGistightSigssaImplresult.nightDayLightUrlService?.let { url ->
            nnigLighGistightSigssaImplresult.nightDayLightSwitchService?.let { switch ->
                callback(
                    url,
                    switch
                )
            }
        }
    }
}