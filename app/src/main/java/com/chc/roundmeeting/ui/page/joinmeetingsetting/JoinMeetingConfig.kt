package com.chc.roundmeeting.ui.page.joinmeetingsetting

data class JoinMeetingConfig(
    val meetingNumber: String,
    val name: String,
    val isOpenMicrophone: Boolean,
    val isOpenLoudspeaker: Boolean,
    val isOpenVideo: Boolean
)
