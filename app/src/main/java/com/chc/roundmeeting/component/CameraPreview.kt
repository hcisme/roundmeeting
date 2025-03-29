package com.chc.roundmeeting.component

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.roundmeeting.MainActivity
import com.chc.roundmeeting.VideoViewModel

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val videoVM = viewModel<VideoViewModel>(context as MainActivity)
    val previewView = remember { PreviewView(context) }
    val cameraProvider = remember { ProcessCameraProvider.getInstance(context).get() }

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
                cameraProvider?.unbindAll()
                cameraProvider?.bindToLifecycle(
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
