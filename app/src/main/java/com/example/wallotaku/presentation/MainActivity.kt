package com.example.wallotaku.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.wallotaku.data.repository.ImageRepositoryImpl
import com.example.wallotaku.di.AppModule
import com.example.wallotaku.domain.usecase.GetImagesUseCase
import com.example.wallotaku.presentation.wallpaper_screen.WallpaperScreen
import com.example.wallotaku.presentation.wallpaper_screen.WallpaperScreenViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val retrofit = AppModule.provideRetrofit()
            val apiService = AppModule.provideUnsplashApiService(retrofit)
            val repository = ImageRepositoryImpl(apiService)
            val getImagesUseCase = GetImagesUseCase(repository)

            WallpaperScreen(viewModel = WallpaperScreenViewModel(getImagesUseCase))
        }
    }
}
