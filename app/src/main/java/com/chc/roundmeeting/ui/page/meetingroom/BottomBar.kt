package com.chc.roundmeeting.ui.page.meetingroom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.roundmeeting.MainActivity
import com.chc.roundmeeting.R
import com.chc.roundmeeting.VideoViewModel
import com.chc.roundmeeting.utils.NumConstants
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    audioPermissionState: PermissionState,
    cameraPermissionState: PermissionState,
    isOpenMicrophone: Boolean,
    isOpenVideo: Boolean,
    onOpenMicrophone: () -> Unit,
    onCloseMicrophone: () -> Unit
) {
    val context = LocalContext.current
    val roomVM = viewModel<RoomViewModel>(context as MainActivity)
    val videoVM = viewModel<VideoViewModel>(context)
    val roomConfig = roomVM.roomConfig!!

    // 麦克风
    val onChangeMicrophoneStatus = {
        if (isOpenMicrophone) {
            roomVM.roomConfig = roomVM.roomConfig!!.copy(isOpenMicrophone = false)
            onCloseMicrophone()
        } else {
            if (audioPermissionState.status.isGranted) {
                roomVM.roomConfig = roomVM.roomConfig!!.copy(isOpenMicrophone = true)
                onOpenMicrophone()
            } else {
                roomVM.showAudioDialog()
            }
        }
    }

    // 视频
    val onChangeVideoStatus = {
        if (isOpenVideo) {
            roomVM.roomConfig = roomVM.roomConfig!!.copy(isOpenVideo = false)
            videoVM.stopCameraProvider()
        } else {
            if (cameraPermissionState.status.isGranted) {
                roomVM.roomConfig = roomVM.roomConfig!!.copy(isOpenVideo = true)

            } else {
                roomVM.showVideoDialog()
            }
        }
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(NumConstants.DEFAULT_BOTTOM_BAR_HEIGHT.dp)
    ) {
        HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)

        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = onChangeMicrophoneStatus
                        )
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Icon(
                        painter = painterResource(
                            if (isOpenMicrophone) R.drawable.microphone_open else R.drawable.microphone_close
                        ),
                        contentDescription = null
                    )
                    Text("麦克风", style = MaterialTheme.typography.labelSmall)
                }
            }

            Box(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = onChangeVideoStatus
                        )
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Icon(
                        painter = painterResource(
                            if (isOpenVideo) R.drawable.video_open else R.drawable.video_close
                        ),
                        contentDescription = null
                    )
                    Text("视频", style = MaterialTheme.typography.labelSmall)
                }
            }

            Box(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {
                                if (roomVM.memberDrawerVisible) {
                                    roomVM.hideMemberDrawer()
                                } else {
                                    roomVM.showMemberDrawer()
                                }
                            }
                        )
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Icon(
                        painter = painterResource(R.drawable.member),
                        contentDescription = null
                    )
                    Text("管理成员(123)", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}
