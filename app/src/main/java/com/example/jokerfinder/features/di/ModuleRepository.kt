package com.example.jokerfinder.features.di

import com.example.jokerfinder.repository.DataRepository
import com.example.jokerfinder.repository.localrepository.LocalRepository
import com.example.jokerfinder.repository.networkreopsitory.NetworkRepository
import dagger.Module
import dagger.Provides


@Module
class ModuleRepository {

    @Provides
    fun repositoryProvider(networkRepository: NetworkRepository, localRepository: LocalRepository) : DataRepository {
        return DataRepository(networkRepository, localRepository)
    }


}