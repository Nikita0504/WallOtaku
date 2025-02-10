package com.example.wallotaku.data.`interface`

import com.example.wallotaku.data.models.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface UnsplashApiService {
    @Headers("User-Agent: Mozilla/5.0 (Linux; Android 14)")
    @GET("nothing")
    suspend fun getImages(
        @Query("count") count: Int = 48,
        @Query("additionalTags") additionalTags: String? = null,
        @Query("blacklistedTags") blacklistedTags: String? = null
    ): ApiResponse
}