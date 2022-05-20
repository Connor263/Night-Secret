package com.happyadda.jaleb

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.happyadda.jaleb.ui.AppContent
import com.happyadda.jaleb.ui.theme.NightSecretTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            NightSecretTheme {
                AppContent()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NightSecretTheme {
        AppContent()
    }
}