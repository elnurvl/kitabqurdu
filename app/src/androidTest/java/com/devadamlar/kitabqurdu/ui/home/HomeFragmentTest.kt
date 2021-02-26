package com.devadamlar.kitabqurdu.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devadamlar.kitabqurdu.R
import junit.framework.TestCase
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest : TestCase() {
    lateinit var scenario: FragmentScenario<Fragment>

    @Before
    fun init() {
        val scenario = launchFragmentInContainer<HomeFragment>()
    }

    @Test
    fun visibilitiesAreCorrect() {
        onView(withId(R.id.searchText)).check(matches(isDisplayed()))
        onView(withId(R.id.searchButton)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.emptyView)).check(matches(not(isDisplayed())))
    }
}