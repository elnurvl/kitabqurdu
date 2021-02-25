package com.devadamlar.kitabqurdu.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devadamlar.kitabqurdu.api.SearchResponse
import com.devadamlar.kitabqurdu.models.Book
import com.devadamlar.kitabqurdu.repositories.BookRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    var books: BehaviorSubject<List<Book>> = BehaviorSubject.createDefault(emptyList())

    fun search(keyword: String): Single<SearchResponse> {
        return bookRepository.search(keyword)
    }
}