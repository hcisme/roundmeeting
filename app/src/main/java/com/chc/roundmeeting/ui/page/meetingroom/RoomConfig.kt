package com.chc.roundmeeting.ui.page.meetingroom

data class RoomConfig(
    /**
     * 加入 或 自己 的会议号
     */
    val meetingNumber: String,
    /**
     * 进入会议使用的名称
     */
    val name: String,
    /**
     * 是否开启麦克风
     */
    val isOpenMicrophone: Boolean,
    /**
     * 是否开启扬声器
     */
    val isOpenLoudspeaker: Boolean,
    /**
     * 是否开启视频
     */
    val isOpenVideo: Boolean
)