package com.devadamlar.kitabqurdu.ui.home.book

import androidx.lifecycle.ViewModel
import com.devadamlar.kitabqurdu.models.Book
import com.devadamlar.kitabqurdu.repositories.BookRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class BookViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

}