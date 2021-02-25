package com.devadamlar.kitabqurdu.di

import com.devadamlar.kitabqurdu.MainActivity
import com.devadamlar.kitabqurdu.ui.about.AboutFragment
import com.devadamlar.kitabqurdu.ui.favorites.FavoritesFragment
import com.devadamlar.kitabqurdu.ui.home.HomeFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(homeFragment: HomeFragment)
    fun inject(favoritesFragment: FavoritesFragment)
    fun inject(aboutFragment: AboutFragment)
}