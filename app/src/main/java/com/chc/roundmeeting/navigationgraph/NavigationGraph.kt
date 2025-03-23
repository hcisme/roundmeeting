package com.chc.roundmeeting.navigationgraph

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chc.roundmeeting.ui.page.home.HomePage
import com.chc.roundmeeting.ui.page.login.LoginPage
import com.chc.roundmeeting.utils.LocalNavController
import com.chc.roundmeeting.utils.LocalSharedPreferences
import com.chc.roundmeeting.utils.getToken

private const val AnimationInDuration = 300
private const val AnimationOutDuration = 300
private val AnimationEasing = LinearOutSlowInEasing
private val enterTransition = slideInHorizontally(
    animationSpec = tween(AnimationInDuration, easing = AnimationEasing), initialOffsetX = { it })
private val exitTransition = slideOutHorizontally(
    animationSpec = tween(AnimationOutDuration, easing = AnimationEasing), targetOffsetX = { it })

@Composable
fun NavigationGraph(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    val sharedPreferences = LocalSharedPreferences.current

    NavHost(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        navController = navController,
        startDestination = if (sharedPreferences.getToken() == null) LOGIN_PAGE else HOME_PAGE,
    ) {
        composable(route = HOME_PAGE) {
            HomePage()
        }

        composable(route = LOGIN_PAGE) {
            LoginPage()
        }
    }
}
