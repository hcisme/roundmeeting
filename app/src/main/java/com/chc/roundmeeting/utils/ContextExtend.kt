package com.chc.roundmeeting.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.core.net.toUri

/**
 * 复制到剪贴板
 */
fun Context.copyToClipboard(label: String, text: String) {
    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboardManager.setPrimaryClip(clip)
}

/**
 * 去设置中的应用界面
 */
fun Context.startSettingActivity(tooltip: String) {
    this.startActivity(
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", this@startSettingActivity.packageName, null)
        }
    )
    Toast.makeText(this, tooltip, Toast.LENGTH_SHORT).show()
}

/**
 * 检查应用是否开启了悬浮窗权限
 */
fun checkOverlayPermission(context: Context, onGranted: () -> Unit) {
    if (!Settings.canDrawOverlays(context)) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            "package:${context.packageName}".toUri()
        )
        (context as Activity).startActivityForResult(intent, 1001)
        Toast.makeText(context, "请开启应用悬浮窗权限", Toast.LENGTH_SHORT).show()
    } else {
        onGranted()
    }
}
