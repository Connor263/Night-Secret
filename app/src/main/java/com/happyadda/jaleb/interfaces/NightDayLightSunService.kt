package com.happyadda.jaleb.interfaces

import com.happyadda.jaleb.data.web.model.NigNightDServiService
import retrofit2.http.GET

interface NightDayLightSunService {
    @GET("42210a00ab49cb64f73a83e6ecf134d6/raw/lvrds:kmot.motzuxcuitacnxijt.iumuojvqv263")
    suspend fun nightDayLightSunServiceOkNightService(): NigNightDServiService
}