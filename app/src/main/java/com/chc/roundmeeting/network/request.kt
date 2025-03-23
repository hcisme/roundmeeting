package com.chc.roundmeeting.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Request {
    private const val CONNECT_TIMEOUT = 15L
    private const val READ_TIMEOUT = 15L
    private const val WRITE_TIMEOUT = 15L

    lateinit var retrofit: Retrofit

    /**
     * 初始化方法
     * @param baseUrl 基础地址
     */
    fun init(baseUrl: String) {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.MINUTES)
            .addInterceptor(createRequestInterceptor())
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
}
