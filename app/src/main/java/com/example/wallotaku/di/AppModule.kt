package com.example.wallotaku.di

import androidx.test.espresso.core.internal.deps.dagger.Provides
import com.example.wallotaku.data.`interface`.UnsplashApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {

    @Provides
    fun provideRetrofit(): Retrofit {



        return Retrofit.Builder()
            .baseUrl("https://api.nekosia.cat/api/v1/images/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideUnsplashApiService(retrofit: Retrofit): UnsplashApiService {
        return retrofit.create(UnsplashApiService::class.java)
    }

}