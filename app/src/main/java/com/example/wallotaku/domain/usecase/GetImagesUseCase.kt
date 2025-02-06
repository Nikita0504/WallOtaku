package com.example.wallotaku.domain.usecase

import com.example.wallotaku.domain.models.ImageModel
import com.example.wallotaku.domain.repository.ImageRepository

class GetImagesUseCase(private val repository: ImageRepository) {
    suspend fun invoke(): List<ImageModel> {
        return repository.fetchImages()
    }
}