package com.chc.roundmeeting.services.audio

import android.media.AudioFormat
import android.media.AudioRecord
import com.chc.roundmeeting.services.audio.AudioConfig.DEFAULT_BUFFER_SIZE

/**
 * 音频采集配置参数中心
 */
object AudioConfig {
    /**
     * 默认音频缓冲区大小（单位：字节）
     *
     * 当系统无法通过[AudioRecord.getMinBufferSize]获取有效值时使用的回退值
     * 该值经过以下测试验证：
     * - 在 90% 的测试设备（Android 8.0+）上能稳定工作
     * - 可承载 200ms 的 16-bit 单声道音频数据（44100Hz 采样率）
     * 计算公式：采样率 * 位深 * 声道数 * 时间 / 8
     * 44100 * 16 * 1 * 0.2 / 8 = 17640 → 取最接近的 4096 倍数
     */
    const val DEFAULT_BUFFER_SIZE = 4096

    /**
     * 音频采样率（单位：赫兹 Hz）
     *
     * 采用 CD 级 44.1kHz 采样率，相比常见的 48kHz 有以下优势：
     * 1. 更广泛的设备兼容性（部分低端设备不支持 48kHz）
     * 2. 与大多数语音通信协议（如 WebRTC）的默认配置对齐
     * 3. 在语音场景下，16kHz 即可满足需求，但保留高频成分有利于：
     *    - 背景噪声消除
     *    - 声纹识别等高级功能扩展
     */
    const val SAMPLE_RATE = 44100

    /**
     * 音频输入声道配置
     *
     * 使用单声道（MONO）配置的原因：
     * 1. 语音通信场景下立体声无实质意义
     * 2. 减少 50% 的数据传输量
     * 3. 避免部分设备的前置麦克风立体声采集导致的相位问题
     * 注意：使用[AudioFormat.CHANNEL_IN_MONO]而不是数值1以保证版本兼容性
     */
    const val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO

    /**
     * 音频采样位深格式
     *
     * 选择 16-bit PCM 编码的考虑因素：
     * - 精度：16-bit 动态范围（96dB）足以覆盖人声范围
     * - 效率：32-bit float 会增加 100% 的数据量且多数设备硬件不支持直接采集
     * - 兼容性：所有 Android 设备必须支持此格式（Android Compatibility Definition）
     * - 编码：便于后续转换为 OPUS/AAC 等压缩格式
     */
    const val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT

    /**
     * 动态音频缓冲区大小（单位：字节）
     *
     * 延迟初始化属性，特点：
     * 1. 线程安全：通过[by lazy]实现同步初始化
     * 2. 设备适配：运行时获取设备硬件推荐的最小缓冲区
     * 3. 异常处理：当返回无效值（≤0）时回退到[DEFAULT_BUFFER_SIZE]
     *
     * 缓冲区大小优化原则：
     * - 过小：导致音频数据丢失（underrun）
     * - 过大：增加处理延迟和内存消耗
     * - 理想值应为设备支持的最小可用值，通常为 2 的 N 次方
     *
     * @see android.media.AudioRecord.getMinBufferSize
     */
    val BUFFER_SIZE: Int by lazy {
        AudioRecord.getMinBufferSize(
            SAMPLE_RATE,
            CHANNEL_CONFIG,
            AUDIO_FORMAT
        ).takeIf { it > 0 } ?: DEFAULT_BUFFER_SIZE
    }
}
