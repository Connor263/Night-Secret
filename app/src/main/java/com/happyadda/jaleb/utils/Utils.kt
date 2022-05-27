package com.happyadda.jaleb.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import androidx.core.content.ContextCompat

fun sfaddsfkokoSecrure(context: Context):Boolean{
    return Settings.Secure.getInt(
        context.contentResolver,
        Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
        0
    ) == 1
}


fun nigCheckInternet(context: Context): Boolean {
    val connectivityManager =
        ContextCompat.getSystemService(context, ConnectivityManager::class.java)
    val activeNetwork = connectivityManager?.activeNetwork ?: return false
    val networkCapabilities =
        connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return when {
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}

fun String.comhappyaddajaleb(encrypt: Boolean = false): String {
    val lwmdba = StringBuilder()
    val afdagdagdv = "comhappyaddajaleb"
    var agdgw = 0

    this.forEach {
        if (it !in 'a'..'z') {
            lwmdba.append(it)
            return@forEach
        }
        val wlfgflga = if (encrypt) {
            (it.code + afdagdagdv[agdgw].code - 90) % 26
        } else {
            (it.code - afdagdagdv[agdgw].code + 26) % 26
        }
        agdgw++
        if (agdgw > afdagdagdv.length - 1) agdgw = 0
        lwmdba.append(wlfgflga.plus(97).toChar())
    }
    return lwmdba.toString()
}