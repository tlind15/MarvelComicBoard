package com.example.marvelboard

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicApi {

    @GET("comics/{comicId}")
    fun fetchComicById(@Path("comicId") comicId: String, @Query("ts") timestamp: Long,
    @Query("apikey") publicKey: String, @Query("hash") hash: String): Call<ResponseBody>
}