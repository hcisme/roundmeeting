package com.chc.roundmeeting.utils

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.chc.roundmeeting.ui.page.joinmeetingsetting.JoinMeetingConfig
import com.chc.roundmeeting.ui.page.quickmeetingsetting.QuickMeetingStorageConfig
import com.google.gson.Gson

fun SharedPreferences.getToken(): String? = this.getString(StorageKeys.KEY_TOKEN, null)

fun SharedPreferences.saveToken(token: String) {
    this.edit {
        putString(StorageKeys.KEY_TOKEN, token).apply()
    }
}

fun SharedPreferences.clearToken() {
    this.edit {
        remove(StorageKeys.KEY_TOKEN).apply()
    }
}

fun SharedPreferences.getJoinMeetingHardwareConfig(): JoinMeetingConfig? {
    val json = this.getString(StorageKeys.KEY_JOIN_MEETING_HARDWARE_CONFIG, null)
    return try {
        Gson().fromJson(json, JoinMeetingConfig::class.java)
    } catch (e: Exception) {
        Log.e("@@", "${e.message}")
        null
    }
}

fun SharedPreferences.saveJoinMeetingHardwareConfig(config: JoinMeetingConfig?) {
    this.edit {
        if (config != null) {
            val json = Gson().toJson(config)
            putString(StorageKeys.KEY_JOIN_MEETING_HARDWARE_CONFIG, json)
        } else {
            remove(StorageKeys.KEY_JOIN_MEETING_HARDWARE_CONFIG)
        }
        apply()
    }
}

fun SharedPreferences.getQuickMeetingHardwareConfig(): QuickMeetingStorageConfig? {
    val json = this.getString(StorageKeys.KEY_QUICK_MEETING_CONFIG, null)
    return try {
        Gson().fromJson(json, QuickMeetingStorageConfig::class.java)
    } catch (e: Exception) {
        Log.e("@@", "${e.message}")
        null
    }
}

fun SharedPreferences.saveQuickMeetingHardwareConfig(config: QuickMeetingStorageConfig?) {
    this.edit {
        if (config != null) {
            val json = Gson().toJson(config)
            putString(StorageKeys.KEY_QUICK_MEETING_CONFIG, json)
        } else {
            remove(StorageKeys.KEY_QUICK_MEETING_CONFIG)
        }
        apply()
    }
}
