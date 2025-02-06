package com.example.wallotaku.presentation.wallpaper_screen

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.wallotaku.domain.models.ImageModel
import com.example.wallotaku.domain.usecase.GetImagesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class WallpaperScreenViewModel(
    private val getImagesUseCase: GetImagesUseCase
) : ViewModel() {

    private val _images = MutableStateFlow<List<ImageModel>>(emptyList())
    val images: StateFlow<List<ImageModel>> get() = _images

    var selectedImage by mutableStateOf<ImageModel?>(null)

    init {
        loadImages()
    }

    fun loadImages() {
        viewModelScope.launch {
            try {
                val fetchedImages = getImagesUseCase.invoke()
                _images.value += fetchedImages
            } catch (e: Exception) {
                println("Ошибка при запросе: $e")
                e.printStackTrace()
            }
        }
    }

    fun onOpenOrDismissDialog(image: ImageModel?) {
        selectedImage = image
    }

    fun setWallpaper(context: Context, imageRequest: ImageRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val imageLoader = ImageLoader(context)
                val drawable = imageLoader.execute(imageRequest).drawable

                if (drawable is BitmapDrawable) {
                    val bitmap = drawable.bitmap
                    val savedImageUri = saveImageToCache(context, bitmap)

                    withContext(Dispatchers.Main) {
                        val intent = Intent(Intent.ACTION_ATTACH_DATA).apply {
                            setDataAndType(savedImageUri, "image/*")
                            putExtra("mimeType", "image/*")
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        context.startActivity(Intent.createChooser(intent, "Выберите способ установки обоев"))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun saveImageToCache(context: Context, bitmap: Bitmap): Uri {
        val cacheDir = File(context.cacheDir, "images").apply { mkdirs() }
        val file = File(cacheDir, "wallpaper_${System.currentTimeMillis()}.jpg")

        FileOutputStream(file).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        }

        return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }

}