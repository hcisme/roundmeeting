package com.chc.roundmeeting.ui.page.meetingroom

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RoomViewModel : ViewModel() {
    var roomConfig by mutableStateOf<RoomConfig?>(null)
    var audioDialogVisible by mutableStateOf(false)
    var videoDialogVisible by mutableStateOf(false)

    fun onRoomConfigChange(newRoomConfig: RoomConfig) {
        roomConfig = newRoomConfig
    }

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

}