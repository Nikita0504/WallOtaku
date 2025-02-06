package com.example.wallotaku.data.`interface`

import com.example.wallotaku.data.models.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Headers


interface UnsplashApiService {
    @Headers("User-Agent: Mozilla/5.0 (Linux; Android 14)")
    @GET("catgirl?count=48")
    suspend fun getImages(): ApiResponse
}