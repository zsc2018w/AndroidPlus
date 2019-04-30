package com.common.net

import android.util.SparseArray
import com.common.basic.BaseApiService
import com.common.basic.BaseApplication
import com.common.utils.AppUtils
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * description:
 * author:  周昇辰
 * date:2019/3/29
 */


 class  HttpUtils private constructor(){
    //retrofit
    private var mRetrofit: Retrofit? = null
    //okHttpClient
    private var okHttpClient: OkHttpClient? = null

    init {
        getRetrofit()
    }

    companion object {
        val TIMEOUT = 10
        val instance: HttpUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            HttpUtils()
        }
    }


   private fun initOkHttp() {
       // val logInterceptor =LogInterceptor()
        val logInterceptor=HttpLoggingInterceptor()
        logInterceptor.level=HttpLoggingInterceptor.Level.BODY
       //设置 请求的缓存的大小跟位置
       val cacheFile = File(BaseApplication.context.cacheDir, "cache")
       val cache = Cache(cacheFile, 1024 * 1024 * 50) //50Mb 缓存的大小

        okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                .addInterceptor(addQueryParameterInterceptor())
                .writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .cache(cache)
                .build()
    }

   private fun getRetrofit() {
        initOkHttp()
        mRetrofit = Retrofit.Builder()
                .baseUrl("https://blog.csdn.net/")
                .client(okHttpClient!!)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
              //  .addConverterFactory(LenientGsonConverterFactory.create())
                //.addConverterFactory(ScalarsConverterFactory.create())
                .build()
    }



    /**
     * 设置公共参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                    // Provide your custom parameter here
                    .addQueryParameter("udid", "d2807c895f0348a180148c9dfa6f2feeac0781b5")
                    .addQueryParameter("deviceModel", AppUtils.getMobileModel())
                    .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }



    fun <T> create(tClass: Class<T>): T {

        return mRetrofit!!.create(tClass)
    }

}
