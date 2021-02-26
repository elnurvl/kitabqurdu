package com.devadamlar.kitabqurdu.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.devadamlar.kitabqurdu.api.BookApi
import com.devadamlar.kitabqurdu.api.PAGE_SIZE
import com.devadamlar.kitabqurdu.models.Book
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val bookApi: BookApi
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun search(keyword: String): Flowable<PagingData<Book>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = { BookPagingSource(bookApi, keyword) }
        ).flowable
    }
}