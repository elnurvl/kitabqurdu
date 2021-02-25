package com.devadamlar.kitabqurdu.repositories

import com.devadamlar.kitabqurdu.api.BookApi
import com.devadamlar.kitabqurdu.api.SearchResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class BookRepository @Inject constructor(
    val bookApi: BookApi
) {
    fun search(keyword: String): Single<SearchResponse> {
        if (keyword.isEmpty()) return Single.create { emptyList<SearchResponse>() }
        return bookApi.searchByTitle(keyword)
    }
}