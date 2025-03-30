package com.chc.roundmeeting.services.floatwindow

import android.app.Application
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.roundmeeting.VideoViewModel
import com.chc.roundmeeting.ui.theme.RoundMeetingTheme

@Composable
fun FloatingWindowContent(
    modifier: Modifier = Modifier,
    service: FloatingWindowService,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    onDrag: (dx: Float, dy: Float) -> Unit,
    onDragEnd: () -> Unit
) {
    CompositionLocalProvider(
        LocalViewModelStoreOwner provides service,
        LocalLifecycleOwner provides service
    ) {
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        val videoVM = viewModel<VideoViewModel>(
            factory = VideoViewModelFactory(service.application)
        )
        val previewView = remember { PreviewView(context) }

        RoundMeetingTheme(
            dynamicColor = false
        ) {
            Box(
                modifier = Modifier
                    .size(width = 100.dp, height = 160.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6F))
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDrag = { change, dragAmount ->
                                change.consume()
                                onDrag(dragAmount.x, dragAmount.y)
                            },
                            onDragEnd = { onDragEnd() }
                        )
                    }
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                FloatingWindowService.stop(context)
                            }
                        )
                    }
            ) {
                AndroidView(
                    modifier = modifier,
                    factory = { ctx ->
                        previewView.apply {
                            this.scaleType = scaleType
                            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        }
                    },
                    update = { view ->
                        // 创建预览用例
                        val preview = Preview.Builder().build().also {
                            it.surfaceProvider = view.surfaceProvider
                        }

                        try {
                            videoVM.cameraProvider?.unbindAll()
                            videoVM.cameraProvider?.bindToLifecycle(
                                lifecycleOwner,
                                cameraSelector,
                                preview,
                                videoVM.imageAnalysis
                            )
                        } catch (e: Exception) {
                            Log.e("@@ CameraPreview", "Camera binding failed", e)
                        }
                    }
                )
            }
        }
    }
}

class VideoViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoViewModel::class.java)) {
            return VideoViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

