package com.devadamlar.kitabqurdu.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.devadamlar.kitabqurdu.models.Book
import com.devadamlar.kitabqurdu.repositories.BookRepository
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    var currentKeyword: String? = null
    var result: Flowable<PagingData<Book>>? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    fun search(keyword: String): Flowable<PagingData<Book>> {
        val lastResult = result
        if (keyword == currentKeyword && lastResult != null) return lastResult
        currentKeyword = keyword
        val newResults = bookRepository.search(keyword).cachedIn(viewModelScope)
        result = newResults
        return newResults
    }
}