package com.chc.roundmeeting.ui.page.quickmeetingsetting

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.roundmeeting.MainActivity
import com.chc.roundmeeting.navigationgraph.NavigationName
import com.chc.roundmeeting.services.MeetingService
import com.chc.roundmeeting.ui.page.meetingroom.RoomConfig
import com.chc.roundmeeting.ui.page.meetingroom.RoomViewModel
import com.chc.roundmeeting.utils.LocalNavController
import com.chc.roundmeeting.utils.NumConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickMeetingSettingPage(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val navController = LocalNavController.current
    val roomVM = viewModel<RoomViewModel>(context as MainActivity)
    val quickMeetingSettingMV = viewModel<QuickMeetingSettingViewModel>(context)
    val config = quickMeetingSettingMV.config

    val enterMeeting = {
        roomVM.onRoomConfigChange(
            RoomConfig(
                meetingNumber = config.personMeetingNumber,
                name = config.name,
                isOpenMicrophone = config.isOpenMicrophone,
                isOpenLoudspeaker = config.isOpenLoudspeaker,
                isOpenVideo = config.isOpenVideo
            )
        )
        MeetingService.start(context)
        navController.navigate(NavigationName.MEETING_ROOM) {
            popUpTo(NavigationName.QUICK_MEETING_SETTING) {
                inclusive = true
            }
        }
    }

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
                expandedHeight = NumConstants.DEFAULT_APPBAR_HEIGHT.dp,
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
                    Text("快速会议", style = MaterialTheme.typography.titleMedium)
                }
            )

            ConfigContent(modifier = Modifier.weight(1F))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.small,
                onClick = enterMeeting
            ) {
                Text("进入会议")
            }
        }
    }
}