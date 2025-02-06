package com.example.wallotaku.presentation.wallpaper_screen.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.wallotaku.domain.models.ImageModel

@Composable
fun WallpaperCard(image: ImageModel, onPress: () -> Unit) {
    Card(
        shape = RoundedCornerShape(size = 12.dp),
        modifier = Modifier
            .padding(10.dp)
            .widthIn(max = 150.dp)
            .heightIn(max = 250.dp)
            .wrapContentSize()
            .clickable { onPress() },
    ) {
        SubcomposeAsyncImage(
            model = image.image.original.url,
            contentDescription = "Wallpaper",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(44, 44, 44)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            },
            error = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(44, 44, 44)),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text("Try again later", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight(weight = 600))
                        Text("Character: ${image.anime.character}", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight(weight = 600))
                    }
                }
            }
        )
    }
}