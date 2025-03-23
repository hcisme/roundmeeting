package com.chc.roundmeeting.ui.page.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import com.chc.roundmeeting.network.service.captchaService
import com.chc.roundmeeting.utils.base64ToImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var captcha by mutableStateOf("")
    var captchaKey by mutableStateOf("")
    var emailError by mutableStateOf("")
    var passwordError by mutableStateOf("")
    var captchaError by mutableStateOf("")
    var captchaBitmap by mutableStateOf<ImageBitmap?>(null)
    var passwordVisible by mutableStateOf(false)

    fun submit() {
        emailError = if (email.isEmpty()) "邮箱为必填项" else ""
        passwordError = if (password.isEmpty()) "密码为必填项" else ""
        captchaError = if (captcha.isEmpty()) "验证码为必填项" else ""

        if (emailError.isEmpty() && passwordError.isEmpty()) {

        }
    }

    suspend fun getCaptcha() {
        val result = withContext(Dispatchers.IO) { captchaService.getCaptcha() }
        captchaBitmap = base64ToImageBitmap(result.data!!.captcha)
        captchaKey = result.data.captchaKey
    }
}