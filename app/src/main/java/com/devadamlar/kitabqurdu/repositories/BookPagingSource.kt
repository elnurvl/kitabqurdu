package com.devadamlar.kitabqurdu.repositories

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.devadamlar.kitabqurdu.api.BookApi
import com.devadamlar.kitabqurdu.api.PAGE_SIZE
import com.devadamlar.kitabqurdu.models.Book
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class BookPagingSource constructor(
    val api: BookApi,
    private val keyword: String
) : RxPagingSource<Int, Book>() {
    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Book>> {
        val position = params.key?: 1
        return api.searchByTitle(keyword, position)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it.docs, position, params.loadSize) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: List<Book>, position: Int, loadSize: Int): LoadResult<Int, Book> {
        val prevPage = if (position == 1) null else position - 1
        val nextPage = if (data.isEmpty()) null else position + (loadSize / PAGE_SIZE)
        return LoadResult.Page(data, prevPage, nextPage)
    }
}