package com.hello.lib_net.net

import androidx.collection.ArrayMap
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Create by jzhan on 2019/8/13
 */
object Retrofit {

    private const val CONNECT_TIME = 15_000L
    private const val WRITE_TIME = 15_000L
    private const val READ_TIME = 15_000L
    private val serviceMap: ArrayMap<String, Any> = ArrayMap()

    @Suppress("UNCHECKED_CAST")
    fun <S> getService(serviceClass: Class<S>, baseUrl: String): S {
        return if (serviceMap.contains(baseUrl)) {
            serviceMap[baseUrl] as S
        } else {
            val service = getDefaultService(baseUrl, serviceClass)
            serviceMap[baseUrl] = service
            service
        }
    }

    /**
     * 默认service
     */
    private fun <S> getDefaultService(baseUrl: String, cls: Class<S>): S {
        return Retrofit.Builder()
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava2适配器
            .baseUrl(baseUrl)
            .build()
            .create(cls)
    }

    /**
     * 创建client
     */
    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIME, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIME, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIME, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    }
}