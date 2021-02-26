package com.devadamlar.kitabqurdu.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val PAGE_SIZE = 100

interface BookApi {
    @GET("search.json")
    fun searchByTitle(@Query("title") title:String, @Query("page") page: Int = 1): Single<SearchResponse>
}