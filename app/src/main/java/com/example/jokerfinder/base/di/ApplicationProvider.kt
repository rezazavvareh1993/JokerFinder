package com.example.jokerfinder.base.di

import android.app.Application
import dagger.Component

//@Component(modules = [ApplicationModule::class])
interface ApplicationProvider {

    fun getApplication() : Application
}