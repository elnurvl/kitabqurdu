package com.devadamlar.kitabqurdu

import android.app.Application
import com.devadamlar.kitabqurdu.di.AppComponent
import com.devadamlar.kitabqurdu.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}