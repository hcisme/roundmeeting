package com.chc.roundmeeting.ui.page.meetingroom

import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.roundmeeting.AudioViewModel
import com.chc.roundmeeting.MainActivity
import com.chc.roundmeeting.component.CameraPreview
import com.chc.roundmeeting.component.Dialog
import com.chc.roundmeeting.utils.PermissionPreferenceManager
import com.chc.roundmeeting.utils.startSettingActivity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MeetingRoomPage(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val audioPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val audioVM = viewModel<AudioViewModel>(context as MainActivity)
    val roomVM = viewModel<RoomViewModel>(context)
    val roomConfig = roomVM.roomConfig
    var isFirstLoad by remember { mutableStateOf(true) }

    fun initPermission() {
        // 麦克风
        if (roomConfig?.isOpenMicrophone == true && !audioPermissionState.status.isGranted) {
            roomVM.showAudioDialog()
        }

        // 视频
        if (roomConfig?.isOpenVideo == true && !cameraPermissionState.status.isGranted) {
            roomVM.showVideoDialog()
        }
    }

    fun initAudio() {
        if (roomVM.getIsOpenMicrophone(audioPermissionState) && !audioVM.recorder.isRecording) {
            audioVM.recorder.start(context)
        }
    }

    LaunchedEffect(Unit) {
        initPermission()
        initAudio()
    }

    LaunchedEffect(audioPermissionState.status.isGranted) {
        if (isFirstLoad && audioPermissionState.status.isGranted && !audioVM.recorder.isRecording) {
            if (roomConfig?.isOpenMicrophone == true) {
                audioVM.recorder.start(context)
            }
            isFirstLoad = false
        }
    }

    if (roomConfig == null) {
        return
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopBar(
                    onClickExitText = {
                        audioVM.recorder.stop()
                    }
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F)
                ) {
                    if (roomVM.getIsOpenVideo(cameraPermissionState)) {
                        CameraPreview(modifier = Modifier.fillMaxSize())
                    }
                    Text("${audioVM.demoNum}")
                }

                BottomBar(
                    audioPermissionState = audioPermissionState,
                    cameraPermissionState = cameraPermissionState,
                    isOpenMicrophone = roomVM.getIsOpenMicrophone(audioPermissionState),
                    isOpenVideo = roomVM.getIsOpenVideo(cameraPermissionState),
                    onOpenMicrophone = {
                        audioVM.recorder.start(context)
                    },
                    onCloseMicrophone = {
                        audioVM.recorder.stop()
                    }
                )
            }
        }

        MemberDrawer()
    }

    Dialog(
        visible = roomVM.audioDialogVisible,
        confirmButtonText = "允许",
        cancelButtonText = "取消",
        onConfirm = {
            PermissionPreferenceManager.managePermissionRequestFlow(
                context = context,
                permissionType = audioPermissionState.permission,
                onFirstRequest = { audioPermissionState.launchPermissionRequest() }
            ) {
                if (audioPermissionState.status.shouldShowRationale) {
                    audioPermissionState.launchPermissionRequest()
                } else {
                    context.startSettingActivity(tooltip = "前往设置 手动开启该应用麦克风权限")
                }
            }
            roomVM.hideAudioDialog()
        },
        onDismissRequest = {
            roomVM.hideAudioDialog()
        }
    ) {
        Text(text = "需要访问麦克风，才能让其他参会者听到您的声音")
    }

    Dialog(
        visible = roomVM.videoDialogVisible,
        confirmButtonText = "允许",
        cancelButtonText = "取消",
        onConfirm = {
            PermissionPreferenceManager.managePermissionRequestFlow(
                context = context,
                permissionType = cameraPermissionState.permission,
                onFirstRequest = { cameraPermissionState.launchPermissionRequest() }
            ) {
                if (cameraPermissionState.status.shouldShowRationale) {
                    cameraPermissionState.launchPermissionRequest()
                } else {
                    context.startSettingActivity(tooltip = "前往设置 手动开启该应用摄像头权限")
                }
            }
            roomVM.hideVideoDialog()
        },
        onDismissRequest = {
            roomVM.hideVideoDialog()
        }
    ) {
        Text(text = "开启摄像头权限，让参会成员看到您的实时画面")
    }

    BackHandler {
        if (roomVM.memberDrawerVisible) {
            roomVM.hideMemberDrawer()
        }
    }
}
