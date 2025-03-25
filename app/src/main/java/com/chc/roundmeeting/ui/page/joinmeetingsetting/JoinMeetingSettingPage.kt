package com.chc.roundmeeting.ui.page.joinmeetingsetting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.roundmeeting.utils.DefaultAppBarHeight
import com.chc.roundmeeting.utils.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinMeetingSettingPage(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    val joinMeetingSettingSV = viewModel<JoinMeetingSettingViewModel>()

    Surface(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(bottom = 16.dp)
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            CenterAlignedTopAppBar(
                expandedHeight = DefaultAppBarHeight.dp,
                navigationIcon = {
                    TextButton(
                        shape = MaterialTheme.shapes.small,
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Text("取消")
                    }
                },
                title = {
                    Text("加入会议", style = MaterialTheme.typography.titleMedium)
                }
            )

            ConfigList(modifier = Modifier.weight(1F))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.small,
                enabled = joinMeetingSettingSV.isEnableJoinMeetingButton,
                onClick = {}
            ) {
                Text("加入会议")
            }
        }
    }
}
