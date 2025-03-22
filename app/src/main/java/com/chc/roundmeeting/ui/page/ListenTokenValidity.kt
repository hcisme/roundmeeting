package com.chc.roundmeeting.ui.page

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chc.roundmeeting.component.Dialog
import com.chc.roundmeeting.navigationgraph.LOGIN_PAGE
import com.chc.roundmeeting.utils.LocalNavController
import com.chc.roundmeeting.utils.LocalSharedPreferences
import com.chc.roundmeeting.utils.clearToken
import com.chc.roundmeeting.utils.getToken

@Composable
fun ListenTokenValidity(modifier: Modifier = Modifier) {
    val sharedPreferences = LocalSharedPreferences.current
    val navController = LocalNavController.current
    var dialogVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (sharedPreferences.getToken() != null && sharedPreferences.getToken() != "123") {
            dialogVisible = true
        }
    }

    Dialog(
        modifier = modifier,
        visible = dialogVisible,
        confirmButtonText = "重新登陆",
        onConfirm = {
            dialogVisible = false
            sharedPreferences.clearToken()
            navController.navigate(LOGIN_PAGE) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }

        }
    ) {
        Text(text = "您的身份验证过期\n请重新登录")
    }
}
