package com.chc.roundmeeting.network

import android.util.Log
import com.chc.roundmeeting.ui.page.AuthViewModel
import com.chc.roundmeeting.utils.NetworkConstants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Request {
    lateinit var retrofit: Retrofit

    /**
     * 初始化方法
     * @param baseUrl 基础地址
     */
    fun init(baseUrl: String, authViewModel: AuthViewModel) {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(NetworkConstants.CONNECT_TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(NetworkConstants.READ_TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(NetworkConstants.WRITE_TIMEOUT, TimeUnit.MINUTES)
            .addInterceptor(createRequestInterceptor())
            .addInterceptor(createResponseInterceptor(authViewModel))
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * 创建Service接口实例
     */
    inline fun <reified T> createService(): T = retrofit.create(T::class.java)

    /**
     * 请求拦截器（添加公共参数/头部）
     */
    private fun createRequestInterceptor() = Interceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Content-Type", "application/json")
            .build()
        chain.proceed(newRequest)
    }

    /**
     * 响应拦截器
     */
    private fun createResponseInterceptor(authViewModel: AuthViewModel) = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val responseBody = response.body

        responseBody?.let {
            val source = it.source()
            source.request(Long.MAX_VALUE)
            val buffer = source.buffer.clone()
            val responseString = buffer.readUtf8()

            try {
                val type = object : TypeToken<BaseResult<*>>() {}.type
                val baseResult = Gson().fromJson<BaseResult<*>>(responseString, type)
                // TODO 使用状态码枚举
                if (baseResult.code == 401) {
                    authViewModel.showLoginDialog()
                }
            } catch (e: Exception) {
                Log.e("@@", "${e.message}")
            }

            // 重建response供后续处理
            response.newBuilder()
                .body(responseString.toResponseBody(it.contentType()))
                .build()
        } ?: response
    }
}
