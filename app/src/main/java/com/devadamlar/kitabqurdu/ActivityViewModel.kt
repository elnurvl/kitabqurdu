package com.devadamlar.kitabqurdu

import android.database.Observable
import androidx.lifecycle.ViewModel
import com.devadamlar.kitabqurdu.models.Book
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class ActivityViewModel : ViewModel() {
    val selectedBook: BehaviorSubject<Book> = BehaviorSubject.create()
}