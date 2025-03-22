package com.chc.roundmeeting.utils

import android.content.SharedPreferences
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

/**
 * 路由 NavHostController
 */
val LocalNavController = compositionLocalOf<NavHostController> { error("No NavController found!") }


val LocalSharedPreferences = compositionLocalOf<SharedPreferences> { error("No SharedPreferences found!") }
