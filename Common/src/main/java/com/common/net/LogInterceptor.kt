package com.common.net

import android.nfc.Tag
import android.util.Log
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

/**
 * description: 打印请求日志
 * author:  周昇辰
 * date:2019/4/1
 */


class LogInterceptor : Interceptor{

    companion object {
        val TAG="http"
    }

    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain?.request()
        val startTime = System.currentTimeMillis()
        val response = chain?.proceed(chain.request())
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime
       // val modifiedUrl = request?.url()?.newBuilder()
        val responesBody = response?.body()
        val mediaType = responesBody?.contentType()
        val content = responesBody.toString()
        Log.d(TAG, "\n")
        Log.d(TAG, "----------Start----------------")
        Log.d(TAG, "| " + request.toString())
        val method = request?.method()
        if ("POST".equals(method)) {
            val sb = StringBuilder()
            if (request?.body() is FormBody) {
                val body = request.body() as FormBody
                for (i in 0 until body.size()) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",")
                }
              //  sb.delete(sb.length - 1, sb.length)
                Log.d(TAG, "| RequestParams:{" + sb.toString() + "}")
            }

        }
        Log.d(TAG, "| Response:$content")
        Log.d(TAG, "----------End:" + duration + "毫秒----------")

        return response?.newBuilder()?.body(ResponseBody.create(mediaType, content))!!.build()
    }


}
