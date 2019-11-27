package com.example.moviefinder.remote

import com.example.moviefinder.remote.movieFinderApi.MovieFinderApiInterface
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvideClass {

    companion object {
        fun provideRetrofit(): MovieFinderApiInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

            return retrofit.create(MovieFinderApiInterface::class.java)
        }
    }
}