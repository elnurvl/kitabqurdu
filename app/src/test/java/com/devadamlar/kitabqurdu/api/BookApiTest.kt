package com.devadamlar.kitabqurdu.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class BookApiTest {
    lateinit var bookApi: BookApi
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createApi() {
        mockWebServer = MockWebServer()
        bookApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                GsonConverterFactory.create(
                GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create())
            )
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(BookApi::class.java)
    }

    @After
    fun stop() {
        mockWebServer.shutdown()
    }

    @Test
    fun testSearch() {
        enqueueResponse("search_result.json")
        val results = bookApi.searchByTitle("test").blockingGet()
        mockWebServer.takeRequest()
        assertThat(results.docs.size, `is`(2))
        assertEquals(2, results.numFound)
        assertEquals("Patrick Gardner", results.docs?.get(0)?.authorName?.get(0))
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("responses/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }
}