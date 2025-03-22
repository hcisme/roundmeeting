package com.chc.roundmeeting.utils

import android.content.SharedPreferences
import androidx.core.content.edit

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
