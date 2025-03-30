package com.chc.roundmeeting

import android.app.Application
import android.graphics.ImageFormat
import android.util.Log
import android.util.Size
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class VideoViewModel(application: Application) : AndroidViewModel(application) {
    val cameraProvider = ProcessCameraProvider.getInstance(getApplication()).get()
    val cameraProviderFuture = ProcessCameraProvider.getInstance(getApplication())
    val videoData = MutableSharedFlow<ByteArray>()
    var frameCount by mutableIntStateOf(0)
    private val resolutionSelector by lazy {
        ResolutionSelector.Builder()
            .setResolutionStrategy(
                ResolutionStrategy(
                    Size(640, 480),  // 目标分辨率
                    ResolutionStrategy.FALLBACK_RULE_CLOSEST_HIGHER  // 回退规则
                )
            )
            .build()
    }
    val imageAnalysis by lazy {
        ImageAnalysis.Builder()
            .setResolutionSelector(resolutionSelector)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(ContextCompat.getMainExecutor(getApplication())) { imageProxy ->
                    if (imageProxy.format == ImageFormat.YUV_420_888) {
                        val buffer = imageProxy.planes[0].buffer
                        val data = ByteArray(buffer.remaining())
                        buffer.get(data)
                        frameCount += 1
                        Log.i("@@", "$frameCount")
                        viewModelScope.launch {
                            videoData.emit(data)
                        }
                    }
                    imageProxy.close()
                }
            }
    }

    fun stopCameraProvider() {
        cameraProviderFuture.get().unbindAll()
    }
}
