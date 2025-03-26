package com.chc.roundmeeting.ui.page.quickmeetingsetting

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.chc.roundmeeting.utils.saveQuickMeetingHardwareConfig

class QuickMeetingSettingViewModel : ViewModel() {
    var config by mutableStateOf(
        QuickMeetingConfig(
            personMeetingNumber = "123965487",
            name = "",
            isOpenMicrophone = true,
            isOpenLoudspeaker = true,
            isOpenVideo = false,
            isUsePersonMeetingNumber = false
        )
    )

    fun onChangeConfig(
        newConfig: QuickMeetingConfig,
        sharedPreferences: SharedPreferences? = null
    ) {
        config = newConfig
        sharedPreferences?.saveQuickMeetingHardwareConfig(
            QuickMeetingStorageConfig(
                isOpenVideo = newConfig.isOpenVideo,
                isUsePersonMeetingNumber = newConfig.isUsePersonMeetingNumber
            )
        )
    }
}