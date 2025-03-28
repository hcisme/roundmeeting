package com.chc.roundmeeting.component

import android.Manifest
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.chc.roundmeeting.utils.PermissionPreferenceManager
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun InitialNotificationPermission() {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)

    LaunchedEffect(Unit) {
        PermissionPreferenceManager.managePermissionRequestFlow(
            context = context,
            permissionType = permissionState.permission,
            onFirstRequest = {
                permissionState.launchPermissionRequest()
            }
        )
    }
}