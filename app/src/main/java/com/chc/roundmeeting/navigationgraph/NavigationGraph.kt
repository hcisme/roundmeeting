package com.chc.roundmeeting.navigationgraph

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.chc.roundmeeting.ui.page.joinmeetingsetting.JoinMeetingSettingPage
import com.chc.roundmeeting.ui.page.login.LoginPage
import com.chc.roundmeeting.ui.page.meetingroom.MeetingRoomPage
import com.chc.roundmeeting.ui.page.quickmeetingsetting.QuickMeetingSettingPage
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
        enterTransition = { fadeIn(animationSpec = tween(500)) },
        exitTransition = { fadeOut(animationSpec = tween(500)) },
        startDestination = if (sharedPreferences.getToken() == null) NavigationName.LOGIN_PAGE else NavigationName.HOME_PAGE,
//        startDestination = NavigationName.JOIN_MEETING_SETTING,
    ) {
        composable(route = NavigationName.HOME_PAGE) {
            HomePage()
        }

        composable(route = NavigationName.LOGIN_PAGE) {
            LoginPage()
        }

        composable(route = NavigationName.JOIN_MEETING_SETTING) {
            JoinMeetingSettingPage()
        }

        composable(route = NavigationName.QUICK_MEETING_SETTING) {
            QuickMeetingSettingPage()
        }

        composable(route = NavigationName.MEETING_ROOM) {
            MeetingRoomPage()
        }
    }
}
