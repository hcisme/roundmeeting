package com.chc.roundmeeting.ui.page.quickmeetingsetting

data class QuickMeetingConfig(
    /**
     * 个人会议号
     */
    val personMeetingNumber: String,
    /**
     * 进入会议使用的名称
     */
    val name: String,
    /**
     * 是否开启麦克风
     */
    val isOpenMicrophone: Boolean = true,
    /**
     * 是否开启扬声器
     */
    val isOpenLoudspeaker: Boolean = true,
    /**
     * 是否开启视频
     */
    val isOpenVideo: Boolean = false,
    /**
     * 是否使用个人会议号
     */
    val isUsePersonMeetingNumber: Boolean = false
)

data class QuickMeetingStorageConfig(
    /**
     * 是否开启视频
     */
    val isOpenVideo: Boolean = false,
    /**
     * 是否使用个人会议号
     */
    val isUsePersonMeetingNumber: Boolean = false
)
