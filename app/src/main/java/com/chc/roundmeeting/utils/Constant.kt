package com.chc.roundmeeting.utils

/**
 * 网络相关配置
 */
object NetworkConstants {
    const val BASE_URL = "https://chcblogs.com"
    const val CONNECT_TIMEOUT = 15L
    const val READ_TIMEOUT = 15L
    const val WRITE_TIMEOUT = 15L
}

/**
 * SharedPreferences 存储键
 */
object StorageKeys {
    const val KEY_TOKEN = "token"
    const val KEY_JOIN_MEETING_HARDWARE_CONFIG = "join_meeting_hardware_config"
    const val KEY_QUICK_MEETING_CONFIG = "quick_meeting_config"
    const val KEY_CLIPBOARD_MEETING_LABEL = "clipboard_meeting_label"
}

/**
 * 测试/演示数据
 */
object TestData {
    const val TEST_AVATAR_URL = "https://avatars.githubusercontent.com/u/11771232312312348716?v=4"
    const val TEXT_DEMO = "圆"
}

object NumConstants {
    /**
     * 默认 AppBar 高度 dp
     */
    const val DEFAULT_APPBAR_HEIGHT = 54

    /**
     * 默认 BottomBar 高度 dp
     */
    const val DEFAULT_BOTTOM_BAR_HEIGHT = 54
}
