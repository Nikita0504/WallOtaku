package com.example.wallotaku.domain.repository

import com.example.wallotaku.domain.models.ImageModel

interface ImageRepository {
    suspend fun fetchImages(additionalTags: String, blacklistedTags: String): List<ImageModel>
}