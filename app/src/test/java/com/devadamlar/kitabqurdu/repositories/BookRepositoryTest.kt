package com.devadamlar.kitabqurdu.repositories

import com.devadamlar.kitabqurdu.api.BookApi
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class BookRepositoryTest : TestCase() {
    val api = mock(BookApi::class.java)
    val bookRepository = BookRepository(api)

    // TODO: Fix!
    @Test
    fun testSearch() {
        bookRepository.search("test")
        verify(api).searchByTitle("test", 1)
    }
}