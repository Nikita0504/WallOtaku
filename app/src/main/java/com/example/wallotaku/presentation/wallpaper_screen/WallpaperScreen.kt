package com.example.wallotaku.presentation.wallpaper_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ModalNavigationDrawer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.wallotaku.presentation.theme.backgroundColor
import com.example.wallotaku.presentation.wallpaper_screen.widgets.CategoriesDialogWindow
import com.example.wallotaku.presentation.wallpaper_screen.widgets.WallpaperDialogWindow
import com.example.wallotaku.presentation.wallpaper_screen.widgets.DrawerContent
import com.example.wallotaku.presentation.wallpaper_screen.widgets.WallpaperCard
import com.example.wallotaku.utils.categories

@Composable
fun WallpaperScreen(viewModel: WallpaperScreenViewModel) {
    val images by viewModel.images.collectAsState()
    val selectedImage = viewModel.selectedImage
    val context = LocalContext.current
    val state = rememberLazyGridState()

    val additionalTags by viewModel.additionalTags.collectAsState()
    val blacklistedTags by viewModel.blacklistedTags.collectAsState()

    val showAdditionalCategoryDialog by viewModel.showAdditionalCategoryDialog.collectAsState()

    val showBlacklistedCategoryDialog by viewModel.showBlacklistedCategoryDialog.collectAsState()

    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                val totalItems = state.layoutInfo.totalItemsCount
                if (lastVisibleIndex != null && lastVisibleIndex >= totalItems - 30) {
                    viewModel.loadImages()
                }
            }
    }

    ModalNavigationDrawer(
        drawerContent = {
            DrawerContent(
                additionalTags = additionalTags,
                blacklistedTags = blacklistedTags,
                onAdditionalTags = { viewModel.openAdditionalCategoryDialog() },
                onBlacklistedTags = { viewModel.openBlacklistedCategoryDialog()},
                onConfirm = {
                    viewModel.reLoadImages()
                },
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor)
                .padding(vertical = 25.dp)
                .then(if (selectedImage != null) Modifier.blur(15.dp) else Modifier)
        ) {
            LazyVerticalGrid(
                state = state,
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(images) { image ->
                    WallpaperCard(image) {
                        viewModel.onOpenOrDismissDialog(image)
                    }
                }
            }

            if (selectedImage != null) {
                val request: ImageRequest = ImageRequest.Builder(LocalContext.current.applicationContext)
                    .data(selectedImage.image.original.url)
                    .crossfade(true)
                    .diskCacheKey(selectedImage.id)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .setHeader("Cache-Control", "max-age=31536000")
                    .build()

                WallpaperDialogWindow(
                    image = selectedImage,
                    onClose = { viewModel.onOpenOrDismissDialog(null) },
                    onSetWallpaper = {
                        viewModel.setWallpaper(context, request)
                    },
                    request = request
                )
            }

            if (showAdditionalCategoryDialog) {
                CategoriesDialogWindow(
                    categories = categories,
                    onSave = { selectedCategories ->
                        if(selectedCategories.isEmpty()){
                            viewModel.onAdditionalCategorySelected(categories.joinToString(","))
                        }
                        else{
                            viewModel.onAdditionalCategorySelected(selectedCategories.joinToString(","))
                        }

                    },
                    selectedCategories = viewModel.additionalTagsTagsSelectedCategories,
                    onDismissRequest = {
                        viewModel.closeAdditionalCategoryDialog()
                    }

                )
            }

            if (showBlacklistedCategoryDialog) {
                CategoriesDialogWindow(
                    categories = categories,
                    onSave = { selectedCategories ->
                        viewModel.onBlacklistedCategorySelected(selectedCategories.joinToString(","))
                    },
                    selectedCategories = viewModel.blacklistedTagsSelectedCategories,
                    onDismissRequest = {
                        viewModel.closeBlacklistedCategoryDialog()
                    }

                )
            }

        }
    }
}
