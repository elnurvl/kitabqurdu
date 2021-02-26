package com.devadamlar.kitabqurdu.ui.home

import com.devadamlar.kitabqurdu.repositories.BookRepository
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class HomeViewModelTest : TestCase() {
    private val repository = mock(BookRepository::class.java)
    lateinit var viewModel: HomeViewModel

    @Before
    fun init() {
        viewModel = HomeViewModel(repository)
    }

    // TODO: Fix!
    @Test
    fun testSearch() {
        viewModel.search("test")
        verify(repository).search("test")
    }
}