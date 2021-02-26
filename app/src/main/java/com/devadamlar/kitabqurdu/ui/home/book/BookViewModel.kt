package com.devadamlar.kitabqurdu.ui.home.book

import androidx.lifecycle.ViewModel
import com.devadamlar.kitabqurdu.repositories.BookRepository
import javax.inject.Inject

class BookViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

}