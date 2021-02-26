package com.devadamlar.kitabqurdu

import androidx.lifecycle.ViewModel
import com.devadamlar.kitabqurdu.models.Book
import io.reactivex.rxjava3.subjects.BehaviorSubject

class ActivityViewModel : ViewModel() {
    val selectedBook: BehaviorSubject<Book> = BehaviorSubject.create()
}