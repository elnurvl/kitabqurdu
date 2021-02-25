package com.devadamlar.kitabqurdu.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {
    @GET("search.json")
    fun searchByTitle(@Query("title") title:String): Single<SearchResponse>
}