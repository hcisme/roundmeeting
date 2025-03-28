package com.chc.roundmeeting.servies.audio

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.abs

class AudioRecorderManager(
    private val onAudioData: (ByteArray) -> Unit = {},
    private val onAudioChange: (Float) -> Unit = {}
) {
    var isRecording = false
    private var audioRecord: AudioRecord? = null
    private var scope: CoroutineScope? = null

    private fun checkPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    private fun createAudioRecord(): AudioRecord = AudioRecord.Builder()
        .setAudioSource(MediaRecorder.AudioSource.MIC)
        .setAudioFormat(
            AudioFormat.Builder()
                .setEncoding(AudioConfig.AUDIO_FORMAT)
                .setSampleRate(AudioConfig.SAMPLE_RATE)
                .setChannelMask(AudioConfig.CHANNEL_CONFIG)
                .build()
        )
        .setBufferSizeInBytes(AudioConfig.BUFFER_SIZE)
        .build()
        .also {
            if (it.state != AudioRecord.STATE_INITIALIZED) {
                throw IllegalStateException("麦克风初始化失败")
            }
        }

    private fun audioStream(context: Context): Flow<ByteArray> = callbackFlow {
        if (!checkPermission(context = context)) {
            close(IllegalStateException("No record permission"))
            return@callbackFlow
        }

        val localRecord = createAudioRecord().apply {
            startRecording()
            isRecording = true
        }
        audioRecord = localRecord

        val buffer = ByteArray(AudioConfig.BUFFER_SIZE)
        try {
            while (isActive) {
                val bytesRead = audioRecord!!.read(buffer, 0, buffer.size)
                when {
                    bytesRead > 0 -> send(buffer.copyOf(bytesRead))
                    bytesRead == AudioRecord.ERROR_INVALID_OPERATION -> break
                    bytesRead == AudioRecord.ERROR_BAD_VALUE -> {
                        close(IllegalStateException("Invalid audio buffer"))
                        break
                    }
                }
            }
        } finally {
            runCatching { localRecord.stop() }
            localRecord.release()
            isRecording = false
            close()
        }
    }.flowOn(Dispatchers.IO)
        .catch { e ->
            isRecording = false
            Log.e("@@ AudioRecorder", "音频流错误", e)
        }

    fun start(context: Context) {
        scope?.cancel()
        scope = CoroutineScope(Dispatchers.IO + SupervisorJob()).apply {
            launch {
                audioStream(context).collect { data ->
                    onAudioData(data)
                    onAudioChange(calculateAmplitude(data))
                }
            }
        }
    }

    private fun calculateAmplitude(buffer: ByteArray): Float {
        val amplitudes = ShortArray(buffer.size / 2)
        ByteBuffer.wrap(buffer)
            .order(ByteOrder.LITTLE_ENDIAN)
            .asShortBuffer()
            .get(amplitudes)

        var max = 0f
        amplitudes.forEach { sample ->
            val normalized = sample.toFloat() / Short.MAX_VALUE
            max = max.coerceAtLeast(abs(normalized))
        }

        return (max * 100).coerceIn(0f, 100f) // 转换为百分比
    }

    fun stop() {
        scope?.cancel()
        scope = null

        runCatching {
            audioRecord?.let {
                if (it.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
                    // 仅在录制状态时调用 stop()
                    it.stop()
                }
                it.release()
            }
        }.onFailure {
            Log.e("@@ AudioRecorder", "Stop error", it)
        }
        audioRecord = null
        isRecording = false
    }
}
