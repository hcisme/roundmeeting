package com.chc.roundmeeting

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.chc.roundmeeting.component.InitialNotificationPermission
import com.chc.roundmeeting.navigationgraph.NavigationGraph
import com.chc.roundmeeting.network.Request
import com.chc.roundmeeting.ui.page.AuthDialog
import com.chc.roundmeeting.ui.page.AuthViewModel
import com.chc.roundmeeting.ui.theme.RoundMeetingTheme
import com.chc.roundmeeting.utils.LocalNavController
import com.chc.roundmeeting.utils.LocalSharedPreferences
import com.chc.roundmeeting.utils.NetworkConstants

class MainActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)

        setContent {
            val navController = rememberNavController()
            val authVM = viewModel<AuthViewModel>()

            LaunchedEffect(Unit) {
                Request.init(baseUrl = NetworkConstants.BASE_URL, authViewModel = authVM)
            }

            InitialNotificationPermission()

            CompositionLocalProvider(
                LocalNavController provides navController,
                LocalSharedPreferences provides sharedPreferences
            ) {
                RoundMeetingTheme {
                    NavigationGraph()

                    AuthDialog()
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val insetsControllerCompat = WindowInsetsControllerCompat(window, window.decorView)
        val nightMode = newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK

        when (nightMode) {
            Configuration.UI_MODE_NIGHT_YES -> {
                insetsControllerCompat.apply {
                    isAppearanceLightStatusBars = false
                    isAppearanceLightNavigationBars = false
                }
            }

            Configuration.UI_MODE_NIGHT_NO -> {
                insetsControllerCompat.apply {
                    isAppearanceLightStatusBars = true
                    isAppearanceLightNavigationBars = true
                }
            }

            else -> {}
        }
    }
}
