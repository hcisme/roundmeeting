package com.chc.roundmeeting.services.camera

import android.media.MediaFormat
import android.util.Size

object VideoConfig {
    const val FRAME_RATE = 30

    /**
     * 2 Mbps
     */
    const val BIT_RATE = 2_000_000

    /**
     * 关键帧间隔（秒）
     */
    const val IFRAME_INTERVAL = 1

    /**
     * H.264
     */
    const val VIDEO_MIME = MediaFormat.MIMETYPE_VIDEO_AVC

    /**
     * HD 720p
     */
    val RESOLUTION = Size(1280, 720)
}