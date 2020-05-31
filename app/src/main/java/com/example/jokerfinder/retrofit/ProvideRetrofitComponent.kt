package com.example.jokerfinder.retrofit

import dagger.Component

@Component(modules = [RetrofitModule::class])
interface ProvideRetrofitComponent {

    fun getRetrofitApiService() : JokerFinderApiService
}