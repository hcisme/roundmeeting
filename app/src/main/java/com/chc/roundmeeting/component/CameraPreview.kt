package com.chc.roundmeeting.component

import android.graphics.ImageFormat
import android.util.Log
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    onImageAvailable: (ByteArray) -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    DisposableEffect(Unit) {
        onDispose {
            // 清理资源
            cameraProviderFuture.get().unbindAll()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            previewView.apply {
                this.scaleType = scaleType
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }
        },
        update = { view ->
            val cameraProvider = try {
                cameraProviderFuture.get()
            } catch (e: Exception) {
                Log.e("@@ CameraPreview", "Camera provider unavailable", e)
                return@AndroidView
            }

            // 创建预览用例
            val preview = Preview.Builder()
                .build()
                .also {
                    it.surfaceProvider = view.surfaceProvider
                }

            val resolutionSelector = ResolutionSelector.Builder()
                .setResolutionStrategy(
                    ResolutionStrategy(
                        Size(640, 480),  // 目标分辨率
                        ResolutionStrategy.FALLBACK_RULE_CLOSEST_HIGHER  // 回退规则
                    )
                )
                .build()

            // 创建图像分析用例
            val imageAnalysis = ImageAnalysis.Builder()
                .setResolutionSelector(resolutionSelector)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
                        if (imageProxy.format == ImageFormat.YUV_420_888) {
                            val buffer = imageProxy.planes[0].buffer
                            val data = ByteArray(buffer.remaining())
                            buffer.get(data)
                            onImageAvailable(data)
                        }
                        imageProxy.close()
                    }
                }

            try {
                cameraProvider.unbindAll()
                // 绑定预览和图像分析用例到生命周期
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageAnalysis
                )
            } catch (e: Exception) {
                Log.e("@@ CameraPreview", "Camera binding failed", e)
            }
        }
    )
}
