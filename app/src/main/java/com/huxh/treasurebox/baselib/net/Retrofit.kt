package com.huxh.treasurebox.baselib.net

import android.provider.Settings
import android.util.Log
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.huxh.treasurebox.BuildConfig
import com.huxh.treasurebox.baselib.AppContext
import com.huxh.treasurebox.baselib.TAG
import com.huxh.treasurebox.baselib.net.interceptor.LoggingInterceptor
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private const val BASE_URL = "https://api.e-wallet.cc/"
private const val TIME_OUT = 30L

val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Log.e(TAG, message)
    }
}).also {
    it.level = HttpLoggingInterceptor.Level.BODY
}

val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
    .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
    .readTimeout(TIME_OUT, TimeUnit.SECONDS).also {
        if (BuildConfig.DEBUG) {
            it.addInterceptor(LoggingInterceptor())
        }
    }
  /*  .addInterceptor {
        val oldResponse = it.proceed(it.request())
        if (!BASE_URL.contains(oldResponse.request.url.host)) {
            oldResponse
        } else {
            val body = oldResponse.body?.string() ?: ""
            val jsonObject = JSONObject(body)
            var code = try {
                jsonObject.getInt("code")
//                if (code == 403) {
//                    logout(AppContext)
//                } else if (code == 406) {
//                    var mnemonicVerified by Preference(Constant.SP.MNEMONIC_VERIFIED, 0)
//                    mnemonicVerified = 0
//                    AppContext.startActivity(
//                        EmptyActivity.newIntent(
//                            AppContext,
//                            EmptyActivity.TO_MNEMONIC_WORDS
//                        )
//                    )
//                } else if (code == 401) {
//                    System.exit(-1)
//                }
            } catch (e: Exception) {
                0
            }
            jsonObject.put("code",code)
            val response = Response.Builder()
                .request(oldResponse.request)
                .protocol(oldResponse.protocol)
                .code(oldResponse.code)
                .message(oldResponse.message)
                .handshake(oldResponse.handshake)
                .headers(oldResponse.headers)
                .body(jsonObject.toString().toByteArray().toResponseBody("application/json".toMediaTypeOrNull()))
                .networkResponse(oldResponse.networkResponse)
                .cacheResponse(oldResponse.cacheResponse)
                .priorResponse(oldResponse.priorResponse)
                .sentRequestAtMillis(oldResponse.sentRequestAtMillis)
                .receivedResponseAtMillis(oldResponse.receivedResponseAtMillis)
                .build()
            response
        }
    }*/
    .cookieJar(PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(AppContext)))
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .build()


object ApiService : RetrofitService by retrofit.create(RetrofitService::class.java)

