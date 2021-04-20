package com.example.jokerfinder.base.di


import dagger.hilt.DefineComponent
import javax.inject.Singleton

/**
 *  A Hilt component that has the lifetime of the application.
 */
@Singleton
@DefineComponent
interface ApplicationComponent {}