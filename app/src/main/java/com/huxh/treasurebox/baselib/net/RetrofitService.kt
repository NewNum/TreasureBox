package com.huxh.treasurebox.baselib.net

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface RetrofitService {

    @Streaming
    @GET
    suspend fun downloadFile(@Url url: String): Response<ResponseBody>

}