package com.example.jokerfinder.base

import android.app.Application
import android.content.Context
import com.example.jokerfinder.base.di.ApplicationComponent
import com.example.jokerfinder.base.di.ApplicationModule
import com.example.jokerfinder.base.di.DaggerApplicationComponent
import com.example.jokerfinder.base.di.RoomModule
import javax.inject.Inject

class BaseApplication : Application() {
    lateinit var component : ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .roomModule(RoomModule(this))
            .build()
    }

    fun getApplicationComponent() : ApplicationComponent{
        return component
    }

}