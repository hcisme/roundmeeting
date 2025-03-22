package com.chc.roundmeeting.ui.page.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var emailError by mutableStateOf("")
    var passwordError by mutableStateOf("")
    var passwordVisible by mutableStateOf(false)

    fun submit() {
        emailError = if (email.isEmpty()) "邮箱为必填项" else ""
        passwordError = if (password.isEmpty()) "密码为必填项" else ""

        if (emailError.isEmpty() && passwordError.isEmpty()) {

        }
    }
}