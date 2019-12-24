package com.example.jokerfinder.base.di

import androidx.lifecycle.ViewModelProvider
import com.example.jokerfinder.base.BaseViewModelFactory
import com.example.jokerfinder.repository.DataRepository
import dagger.Module
import dagger.Provides

@Module
class BaseViewModelFactoryModule  {
    @Provides
    fun provideViewModelFactory(repository: DataRepository) : ViewModelProvider.Factory{
        return BaseViewModelFactory(repository)
    }
}