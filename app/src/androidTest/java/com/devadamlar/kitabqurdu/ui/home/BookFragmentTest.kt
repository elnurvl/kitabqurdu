package com.devadamlar.kitabqurdu.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devadamlar.kitabqurdu.R
import com.devadamlar.kitabqurdu.ui.home.book.BookFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookFragmentTest {
    lateinit var scenario: FragmentScenario<Fragment>

    @Before
    fun init() {
        val scenario = launchFragmentInContainer<BookFragment>()
    }

    @Test
    fun visibilitiesAreCorrect() {
        onView(withId(R.id.titleText)).check(matches(isDisplayed()))
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
    }
}