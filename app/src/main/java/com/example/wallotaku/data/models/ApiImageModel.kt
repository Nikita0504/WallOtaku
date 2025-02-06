package com.example.wallotaku.data.models

data class ApiImageModel(
    val id: String,
    val image: Image,
    val metadata: Metadata,
    val category: String,
    val tags: List<String>,
    val rating: String,
    val anime: Anime,
    val colors: Colors,
)

data class Image(
    val original: Original,
)

data class Original(
    val url: String,
    val extension: String,
)

data class Metadata(
    val original: Original2,
)

data class Original2(
    val height: Long,
)

data class Anime(
    val title: Any?,
    val character: Any?,
)


data class Colors(
    val main: String,
    val palette: List<String>
)
