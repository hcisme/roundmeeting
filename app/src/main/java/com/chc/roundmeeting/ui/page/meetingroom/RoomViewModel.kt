package com.chc.roundmeeting.ui.page.meetingroom

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted

class RoomViewModel : ViewModel() {
    var roomConfig by mutableStateOf<RoomConfig?>(null)
    var audioDialogVisible by mutableStateOf(false)
    var videoDialogVisible by mutableStateOf(false)
    var memberDrawerVisible by mutableStateOf(false)

    fun onRoomConfigChange(newRoomConfig: RoomConfig) {
        roomConfig = newRoomConfig
    }

    @OptIn(ExperimentalPermissionsApi::class)
    fun getIsOpenMicrophone(audioPermissionState: PermissionState) =
        audioPermissionState.status.isGranted && roomConfig?.isOpenMicrophone == true

    @OptIn(ExperimentalPermissionsApi::class)
    fun getIsOpenVideo(cameraPermissionState: PermissionState) =
        cameraPermissionState.status.isGranted && roomConfig?.isOpenVideo == true


    fun showAudioDialog() {
        audioDialogVisible = true
    }

    fun hideAudioDialog() {
        audioDialogVisible = false
    }

    fun showVideoDialog() {
        videoDialogVisible = true
    }

    fun hideVideoDialog() {
        videoDialogVisible = false
    }

    fun showMemberDrawer() {
        memberDrawerVisible = true
    }

    fun hideMemberDrawer() {
        memberDrawerVisible = false
    }
}