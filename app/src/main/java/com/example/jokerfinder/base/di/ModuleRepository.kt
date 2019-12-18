package com.example.jokerfinder.base.di

import com.example.jokerfinder.base.BaseApplication
import com.example.jokerfinder.repository.DataRepository
import com.example.jokerfinder.repository.networkreopsitory.NetworkRepository
import dagger.Module
import dagger.Provides


@Module
class ModuleRepository {

    @Provides
    fun repositoryProvider(networkRepository: NetworkRepository, baseApplication: BaseApplication ) : DataRepository {
        return DataRepository(networkRepository, baseApplication)
    }


}