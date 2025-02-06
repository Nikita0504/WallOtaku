package com.example.wallotaku.data.models

data class ApiResponse(
    val success: Boolean,
    val status: Int,
    val count: Int,
    val images: List<ApiImageModel>
)