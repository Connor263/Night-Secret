package com.happyadda.jaleb

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.happyadda.jaleb.ui.AppContent
import com.happyadda.jaleb.ui.theme.NightSecretTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        installSplashScreen().setOnExitAnimationListener { splashView ->
            ObjectAnimator.ofFloat(
                splashView.view,
                View.ALPHA,
                1F,
                0F
            ).apply {
                interpolator = AccelerateInterpolator()
                duration = 300L
                doOnEnd { splashView.remove() }
                start()
            }
        }


        setContent {
            val navController = rememberNavController()
            NightSecretTheme {
                AppContent(navController)
            }
        }
    }
}