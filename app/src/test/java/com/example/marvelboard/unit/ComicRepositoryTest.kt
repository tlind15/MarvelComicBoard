package com.example.marvelboard.unit

import com.example.marvelboard.Comic
import com.example.marvelboard.ComicApi
import com.example.marvelboard.ComicRepository
import com.example.marvelboard.utils.Clock
import com.example.marvelboard.utils.MarvelKeyHandler
import com.example.marvelboard.utils.MarvelRestHandler
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class ComicRepositoryTest {

    @MockK
    private lateinit var comicApiMock: ComicApi

    @MockK
    private lateinit var callMock: Call<ResponseBody>

    @MockK
    private lateinit var marvelRestHandlerMock: MarvelRestHandler

    @MockK
    private lateinit var marvelKeyHandlerMock: MarvelKeyHandler

    @MockK
    private lateinit var clockMock: Clock

    private val responseJsonStringFake = "{\"code\":200,\"status\":\"Ok\",\"copyright\":\"© " +
            "2021 MARVEL\",\"attributionText\":\"Data provided by Marvel. © 2021 MARVEL\"," +
            "\"attributionHTML\":\"<a href=\\\"http://marvel.com\\\">Data provided by Marvel. © " +
            "2021 MARVEL</a>\",\"etag\":\"40c6cbd554c3577ec7b1db955274ae0daf416dc6\"," +
            "\"data\":{\"offset\":0,\"limit\":20,\"total\":1,\"count\":1,\"results\":[{\"id\":331," +
            "\"digitalId\":0,\"title\":\"Gun Theory (2003) #4\",\"issueNumber\":4," +
            "\"variantDescription\":\"\",\"description\":\"The phone rings, and killer-for-hire " +
            "Harvey embarks on another hit. But nothing's going right this job. There's little room" +
            " for error in the business of killing - so what happens when one occurs?" +
            "\\r\\n32 PGS./ PARENTAL ADVISORY ...\$2.50\",\"modified\":\"-0001-11-30T00:00:00-0500\"," +
            "\"isbn\":\"\",\"upc\":\"5960605468-00111\",\"diamondCode\":\"\",\"ean\":\"\"," +
            "\"issn\":\"\",\"format\":\"Comic\",\"pageCount\":0," +
            "\"textObjects\":[{\"type\":\"issue_solicit_text\",\"language\":\"en-us\"," +
            "\"text\":\"The phone rings, and killer-for-hire Harvey embarks on another hit." +
            " But nothing's going right this job. There's little room for error in the business of" +
            " killing - so what happens when one occurs?\\r\\n32 PGS./ PARENTAL ADVISORY ...\$2.50\"}]," +
            "\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/331\"," +
            "\"urls\":[{\"type\":\"detail\",\"url\":\"http://marvel.com/comics/issue/331/gun_theory" +
            "_2003_4?utm_campaign=apiRef&utm_source=09ad6283b567a6f3271f4d314dd97d0d\"}]," +
            "\"series\":{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/649\"," +
            "\"name\":\"Gun Theory (2003)\"},\"variants\":[],\"collections\":[]," +
            "\"collectedIssues\":[],\"dates\":[{\"type\":\"onsaleDate\"," +
            "\"date\":\"2029-12-31T00:00:00-0500\"},{\"type\":\"focDate\"," +
            "\"date\":\"-0001-11-30T00:00:00-0500\"}],\"prices\":[{\"type\":\"printPrice\"," +
            "\"price\":2.5}],\"thumbnail\":{\"path\":\"http://i.annihil.us/u/prod/marvel/i/" +
            "mg/c/60/4bc69f11baf75\",\"extension\":\"jpg\"},\"images\":[{\"path\":\"http://i.annihil." +
            "us/u/prod/marvel/i/mg/c/60/4bc69f11baf75\",\"extension\":\"jpg\"}]," +
            "\"creators\":{\"available\":0,\"collectionURI\":\"http://gateway.marvel.com/v1/public/" +
            "comics/331/creators\",\"items\":[],\"returned\":0}," +
            "\"characters\":{\"available\":0,\"collectionURI\":\"http://gateway.marvel.com/v1/public/" +
            "comics/331/characters\",\"items\":[],\"returned\":0},\"stories\":{\"available\":2," +
            "\"collectionURI\":\"http://gateway.marvel.com/v1/public/comics/331/stories\"," +
            "\"items\":[{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/2263\"," +
            "\"name\":\"Interior #2263\",\"type\":\"interiorStory\"}," +
            "{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/65423\"," +
            "\"name\":\"GUN THEORY 4 cover\",\"type\":\"cover\"}],\"returned\":2}," +
            "\"events\":{\"available\":0,\"collectionURI\":\"http://gateway.marvel.com/v1/public/" +
            "comics/331/events\",\"items\":[],\"returned\":0}}]}}"

    private lateinit var comicRepository: ComicRepository



    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        comicRepository = ComicRepository(comicApiMock, marvelRestHandlerMock, marvelKeyHandlerMock, clockMock)

        every { comicApiMock.fetchComicById(any(), any(), any(), any()) } returns callMock
    }

    @Test
    fun verifyComicCreatedFromApi() {
        val comicIdFake = "331"
        val timestampFake = 1L
        val publicKeyFake = "1234"
        val privateKeyFake = "5678"

        val responseMock = mockk<Response<ResponseBody>>()
        val responseBodyMock = mockk<ResponseBody>()

        every { clockMock.millis() } returns timestampFake
        every { marvelKeyHandlerMock.publicKey } returns publicKeyFake
        every { marvelKeyHandlerMock.privateKey } returns privateKeyFake
        coEvery { marvelRestHandlerMock.makeRequest(any()) } returns responseMock
        every { responseMock.isSuccessful } returns true
        every { responseMock.body() } returns responseBodyMock
        every { responseBodyMock.string() } returns responseJsonStringFake

        val returnedComic: Comic? = runBlocking {
            comicRepository.fetchComicById(comicIdFake)
        }

        val expectedHashFake = "db330ea6f9760feb1a9b16d49382d1bf"
        val expectedComicTitle = "Gun Theory (2003) #4"
        val expectedComicDescription = "The phone rings, and killer-for-hire Harvey embarks on another hit." +
                " But nothing's going right this job. There's little room for error in the business of" +
                " killing - so what happens when one occurs?\r\n32 PGS./ PARENTAL ADVISORY ...\$2.50"
        val expectedComicImageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/c/60/4bc69f11baf75.jpg"

        assertEquals(expectedComicTitle, returnedComic?.title)
        assertEquals(expectedComicDescription, returnedComic?.description)
        assertEquals(expectedComicImageUrl, returnedComic?.coverImageUrl)

        verify(exactly = 1) {
            comicApiMock.fetchComicById(comicIdFake, timestampFake, publicKeyFake, expectedHashFake)
        }
        coVerify(exactly = 1) { marvelRestHandlerMock.makeRequest(callMock) }
    }
}