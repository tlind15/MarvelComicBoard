package com.example.marvelboard.utils

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MarvelRestHandler @Inject constructor() {

    suspend fun makeRequest(call: Call<ResponseBody>): Response<ResponseBody> {
        return suspendCoroutine<Response<ResponseBody>> {
            call.enqueue(object: Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    it.resume(response)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    it.resumeWithException(t)
                }
            })
        }
    }
}