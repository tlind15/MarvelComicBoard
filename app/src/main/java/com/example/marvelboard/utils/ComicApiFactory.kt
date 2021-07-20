package com.example.marvelboard.utils

import com.example.marvelboard.ComicApi
import retrofit2.Retrofit
import javax.inject.Inject

class ComicApiFactory @Inject constructor(){

    fun createApi(baseUrl: String): ComicApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()
            .create(ComicApi::class.java)
    }
}