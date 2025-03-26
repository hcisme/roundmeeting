package com.chc.roundmeeting.ui.page

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.roundmeeting.component.Dialog
import com.chc.roundmeeting.navigationgraph.NavigationName
import com.chc.roundmeeting.utils.LocalNavController
import com.chc.roundmeeting.utils.LocalSharedPreferences
import com.chc.roundmeeting.utils.clearToken

@Composable
fun AuthDialog(modifier: Modifier = Modifier) {
    val sharedPreferences = LocalSharedPreferences.current
    val navController = LocalNavController.current
    val authVM = viewModel<AuthViewModel>()

    LaunchedEffect(Unit) {
        // TODO 调取接口 判端token是否有效
    }

    Dialog(
        modifier = modifier,
        visible = authVM.loginDialogVisible,
        confirmButtonText = "重新登陆",
        onConfirm = {
            authVM.hideLoginDialog()
            sharedPreferences.clearToken()
            navController.navigate(NavigationName.LOGIN_PAGE) {
                popUpTo(NavigationName.HOME_PAGE) {
                    inclusive = true
                }
            }
        }
    ) {
        Text(text = "您的身份验证过期\n请重新登录")
    }
}
