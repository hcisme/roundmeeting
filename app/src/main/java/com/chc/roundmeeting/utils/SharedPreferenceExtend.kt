package com.chc.roundmeeting.utils

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.chc.roundmeeting.ui.page.joinmeetingsetting.JoinMeetingConfig
import com.google.gson.Gson

fun SharedPreferences.getToken(): String? = this.getString(KEY_TOKEN, null)

fun SharedPreferences.saveToken(token: String) {
    this.edit {
        putString(KEY_TOKEN, token).apply()
    }
}

fun SharedPreferences.clearToken() {
    this.edit {
        remove(KEY_TOKEN).apply()
    }
}

fun SharedPreferences.getJoinMeetingHardwareConfig(): JoinMeetingConfig? {
    val json = this.getString(KEY_JOIN_MEETING_HARDWARE_CONFIG, null)
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
            putString(KEY_JOIN_MEETING_HARDWARE_CONFIG, json)
        } else {
            remove(KEY_JOIN_MEETING_HARDWARE_CONFIG)
        }
        apply()
    }
}

fun SharedPreferences.clearJoinMeetingHardwareConfig() {
    this.edit {
        remove(KEY_JOIN_MEETING_HARDWARE_CONFIG)
        apply()
    }
}
