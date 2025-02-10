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
import com.example.wallotaku.utils.categories
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

    private var _additionalTags = MutableStateFlow("")
    val additionalTags: StateFlow<String> get() = _additionalTags

    private var _blacklistedTags = MutableStateFlow("")
    val blacklistedTags: StateFlow<String> get() = _blacklistedTags

    private val _showAdditionalCategoryDialog = MutableStateFlow(false)
    val showAdditionalCategoryDialog: StateFlow<Boolean> get() = _showAdditionalCategoryDialog

    private val _showBlacklistedCategoryDialog = MutableStateFlow(false)
    val showBlacklistedCategoryDialog: StateFlow<Boolean> get() = _showBlacklistedCategoryDialog

    var additionalTagsTagsSelectedCategories = mutableStateOf(setOf<String>())
    var blacklistedTagsSelectedCategories = mutableStateOf(setOf<String>())


    init {
        loadImages()
    }

    fun loadImages() {
        viewModelScope.launch {

            try {
                val fetchedImages = getImagesUseCase.invoke(_additionalTags.value.ifEmpty { categories.joinToString(",") }, _blacklistedTags.value).distinctBy { it.id }

                val currentList = _images.value.toMutableList()
                val newImages = fetchedImages.filter { newImage ->
                    currentList.none { it.id == newImage.id }
                }

                if (newImages.isNotEmpty()) {
                    _images.value += newImages
                }

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

    fun reLoadImages(){
        _images.value = emptyList()
        loadImages()
    }

    fun openAdditionalCategoryDialog() {
        _showAdditionalCategoryDialog.value = true
    }

    fun closeAdditionalCategoryDialog() {
        _showAdditionalCategoryDialog.value = false
    }

    fun onAdditionalCategorySelected(categories: String) {
        _additionalTags.value = categories
        _showAdditionalCategoryDialog.value = false
    }

    fun openBlacklistedCategoryDialog() {
        _showBlacklistedCategoryDialog.value = true
    }

    fun closeBlacklistedCategoryDialog() {
        _showBlacklistedCategoryDialog.value = false
    }

    fun onBlacklistedCategorySelected(categories: String) {
        _blacklistedTags.value = categories
        _showBlacklistedCategoryDialog.value = false
    }

}