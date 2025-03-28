package com.chc.roundmeeting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.chc.roundmeeting.servies.audio.AudioRecorderManager

class AudioViewModel : ViewModel() {
    var demoNum by mutableFloatStateOf(0F)
    val recorder = AudioRecorderManager { demoNum = it }
}