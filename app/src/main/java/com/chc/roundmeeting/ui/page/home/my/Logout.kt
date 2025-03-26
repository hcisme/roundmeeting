package com.chc.roundmeeting.ui.page.home.my

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.roundmeeting.component.Dialog
import com.chc.roundmeeting.navigationgraph.NavigationName
import com.chc.roundmeeting.utils.LocalNavController
import com.chc.roundmeeting.utils.LocalSharedPreferences
import com.chc.roundmeeting.utils.clearToken

@Composable
fun Logout(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    val sharedPreferences = LocalSharedPreferences.current
    val myVM = viewModel<MyViewModel>()

    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(52.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(CardDefaults.cardColors().containerColor)
            .clickable {
                // TODO 退出登录逻辑
                myVM.logoutDialogVisible = true
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "退出登录",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.error
        )
    }

    Dialog(
        modifier = modifier,
        visible = myVM.logoutDialogVisible,
        confirmButtonText = "退出登录",
        cancelButtonText = "取消",
        onConfirm = {
            myVM.logoutDialogVisible = false
            sharedPreferences.clearToken()
            navController.navigate(NavigationName.LOGIN_PAGE) {
                popUpTo(NavigationName.HOME_PAGE) {
                    inclusive = true
                }
            }
        },
        onDismissRequest = {
            myVM.logoutDialogVisible = false
        }
    ) {
        Text(text = "您确定退出登录吗？")
    }
}
