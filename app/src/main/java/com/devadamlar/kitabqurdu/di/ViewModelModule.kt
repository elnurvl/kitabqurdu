package com.devadamlar.kitabqurdu.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devadamlar.kitabqurdu.ui.about.AboutViewModel
import com.devadamlar.kitabqurdu.ui.favorites.FavoritesViewModel
import com.devadamlar.kitabqurdu.ui.home.HomeViewModel
import com.devadamlar.kitabqurdu.ui.home.book.BookViewModel
import com.devadamlar.kitabqurdu.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    abstract fun bindFavoritesViewModel(favoritesViewModel: FavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AboutViewModel::class)
    abstract fun bindAboutViewModel(aboutViewModel: AboutViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookViewModel::class)
    abstract fun bindBookViewModel(bookViewModel: BookViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}