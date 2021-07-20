package com.example.marvelboard

import com.example.marvelboard.utils.Clock
import com.example.marvelboard.utils.MarvelKeyHandler
import com.example.marvelboard.utils.MarvelRestHandler
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

class ComicRepository @Inject constructor(private val comicApi: ComicApi, private val marvelRestHandler: MarvelRestHandler,
                      private val marvelKeyHandler: MarvelKeyHandler, private val clock: Clock) {

    suspend fun fetchComicById(comicId: String): Comic? {
        val time = clock.millis()
        val apiCall: Call<ResponseBody> = comicApi.fetchComicById(comicId, time,
            marvelKeyHandler.publicKey, createHash(time))
        val response: Response<ResponseBody> = marvelRestHandler.makeRequest(apiCall)
        return createComic(response.body())
    }

    private fun createComic(responseBody: ResponseBody?): Comic? {
        return responseBody?.string()?.let {
            val resultsJsonArray: JsonArray = JsonParser.parseString(it).asJsonObject["data"].asJsonObject["results"].asJsonArray
            if (resultsJsonArray.size() > 0) {
                val dataJson = resultsJsonArray.get(0).asJsonObject
                val title = dataJson["title"].asString
                val description = dataJson["description"].asString
                val coverImagePath = dataJson["thumbnail"].asJsonObject["path"].asString
                val coverImageExtension = dataJson["thumbnail"].asJsonObject["extension"].asString
                Comic(title, description, "${coverImagePath}.${coverImageExtension}")
            } else { null }
        }
    }

    private fun createHash(timestamp: Long): String {
        val messageDigest = MessageDigest.getInstance("MD5")
        return messageDigest
            .digest("${timestamp}${marvelKeyHandler.privateKey}${marvelKeyHandler.publicKey}".toByteArray())
            .toHexString()
    }

    private fun ByteArray.toHexString(): String = joinToString("") { "%02x".format(it) }
}

@Module
@InstallIn(SingletonComponent::class)
object ComicRepositoryModule {

    @Singleton
    @Provides
    fun provideComicApi(): ComicApi {
        return Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/v1/public/")
            .build()
            .create(ComicApi::class.java)
    }
}