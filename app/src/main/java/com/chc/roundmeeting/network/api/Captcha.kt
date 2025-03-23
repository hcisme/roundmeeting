package com.chc.roundmeeting.network.api

import com.chc.roundmeeting.network.BaseResult
import com.chc.roundmeeting.network.model.CaptchaModel
import retrofit2.http.GET

interface CaptchaApi {
    @GET("/api/web/account/captcha")
    suspend fun getCaptcha(): BaseResult<CaptchaModel>
}
