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
            meetingNumber = "2312731623",
            name = "池海成",
            isOpenMicrophone = false,
            isOpenLoudspeaker = false,
            isOpenVideo = false
        )
    )

    fun onChangeConfig(newConfig: JoinMeetingConfig, sharedPreferences: SharedPreferences? = null) {
        config = newConfig
        sharedPreferences?.saveJoinMeetingHardwareConfig(newConfig)
    }
}