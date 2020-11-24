package com.huxh.treasurebox.baselib.net.interceptor

import com.huxh.treasurebox.baselib.utils.LogUtil
import com.huxh.treasurebox.baselib.utils.toJson
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.URLDecoder

class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        val request = chain.request()
        val t1 = System.nanoTime() //请求发起的时间
        val method = request.method
        if ("POST" == method) {
            val convertedString = URLDecoder.decode(toJson(request.body), "UTF-8")
            LogUtil.releaseLog(
                String.format(
                    "发送请求 %s RequestParams: %n %s",
                    request.url, convertedString
                )
            )
        } else {
            LogUtil.releaseLog(
                String.format(
                    "发送请求 %s %n %s",
                    request.url, request.headers
                )
            )
        }
        val response = chain.proceed(request)
        val t2 = System.nanoTime() //收到响应的时间
        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        val responseBody = response.peekBody(1024 * 1024.toLong())
        LogUtil.d(
            String.format(
                "接收响应: [%s] %.1fms 返回json: ",
                response.request.url, (t2 - t1) / 1e6
            )
        )
        LogUtil.d(responseBody.string())

//            SystemClock.sleep(5000);
        return response
    }
}