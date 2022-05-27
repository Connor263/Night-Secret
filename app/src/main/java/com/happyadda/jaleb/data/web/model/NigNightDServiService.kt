package com.happyadda.jaleb.data.web.model

import com.google.gson.annotations.SerializedName

data class NigNightDServiService(
    @SerializedName("url")
    val nightDayLightUrlService: String? = null,

    @SerializedName("switch")
    val nightDayLightSwitchService: Boolean? = null
)
