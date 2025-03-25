package com.chc.roundmeeting.utils

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.Settings
import android.util.Base64
import android.widget.Toast
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

/**
 * 将 Base64 转为 Bitmap
 */
fun base64ToImageBitmap(base64String: String): ImageBitmap {
    // 移除数据 URI 方案的部分
    val pureBase64Encoded = base64String.substring(base64String.indexOf(",") + 1)

    // 解码 Base64 字符串为字节数组
    val imageBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT)

    // 将字节数组转换为 Bitmap
    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

    // 将 Bitmap 转换为 ImageBitmap 并返回
    return bitmap.asImageBitmap()
}

/**
 * 是否是汉字
 */
fun isChineseChar(c: Char): Boolean = c in '\u4E00'..'\u9FFF'

fun Context.startSettingActivity(tooltip: String) {
    this.startActivity(
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", this@startSettingActivity.packageName, null)
        }
    )
    Toast.makeText(this, tooltip, Toast.LENGTH_SHORT).show()
}
