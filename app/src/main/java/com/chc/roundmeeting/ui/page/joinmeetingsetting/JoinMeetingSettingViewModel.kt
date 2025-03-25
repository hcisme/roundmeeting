package com.chc.roundmeeting.ui.page.joinmeetingsetting

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.chc.roundmeeting.utils.saveJoinMeetingHardwareConfig

class JoinMeetingSettingViewModel : ViewModel() {
    var config by mutableStateOf(
        JoinMeetingConfig(
            meetingNumber = "",
            name = "",
            isOpenMicrophone = false,
            isOpenLoudspeaker = false,
            isOpenVideo = false
        )
    )
    var isEnableJoinMeetingButton by mutableStateOf(false)
    var audioDialogVisible by mutableStateOf(false)
    var videoDialogVisible by mutableStateOf(false)

    fun onChangeConfig(newConfig: JoinMeetingConfig, sharedPreferences: SharedPreferences? = null) {
        config = newConfig
        isEnableJoinMeetingButton = newConfig.meetingNumber != "" && newConfig.name != ""
        sharedPreferences?.saveJoinMeetingHardwareConfig(newConfig)
    }
}