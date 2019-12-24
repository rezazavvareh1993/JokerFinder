package com.example.jokerfinder.base.di

import android.app.Application
import com.example.jokerfinder.base.BaseApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: BaseApplication) {

    @Provides
    fun provideApplication() : Application{
        return application
    }
}