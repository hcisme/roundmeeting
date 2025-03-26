package com.chc.roundmeeting.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast

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

