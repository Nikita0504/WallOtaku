package com.example.wallotaku.data.repository

import com.example.wallotaku.data.`interface`.UnsplashApiService
import com.example.wallotaku.domain.models.Anime
import com.example.wallotaku.domain.models.Colors
import com.example.wallotaku.domain.models.Image
import com.example.wallotaku.domain.models.ImageModel
import com.example.wallotaku.domain.models.Metadata
import com.example.wallotaku.domain.models.Original
import com.example.wallotaku.domain.models.Original2
import com.example.wallotaku.domain.repository.ImageRepository

class ImageRepositoryImpl(
    private val apiService: UnsplashApiService
) : ImageRepository {
    override suspend fun fetchImages(additionalTags: String, blacklistedTags: String): List<ImageModel> {
        val response = apiService.getImages(
            count = 48,
            additionalTags = additionalTags,
            blacklistedTags = blacklistedTags
        )
        val images = response.images

        return images.map { imageItem ->

            ImageModel(
                id = imageItem.id,
                image = Image(
                    original = Original(
                        url = imageItem.image.original.url,
                        extension = imageItem.image.original.extension
                    ),
                ),
                metadata = Metadata(
                    original = Original2(
                        height = imageItem.metadata.original.height,
                    ),
                ),
                category = imageItem.category,
                tags = imageItem.tags,
                rating = imageItem.rating,
                anime = Anime(
                    title = imageItem.anime.title ?: "Unknown",
                    character = imageItem.anime.character ?: "Custom"
                ),
                colors = Colors(
                    main = imageItem.colors.main,
                    palette = imageItem.colors.palette
                ),
            )
        }
    }
}