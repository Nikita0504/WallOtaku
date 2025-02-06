package com.example.wallotaku.presentation.wallpaper_screen.widgets

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.wallotaku.domain.models.ImageModel

@Composable
fun DialogWindow(image: ImageModel, onClose: () -> Unit, onSetWallpaper: () -> Unit, request: ImageRequest) {

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        val gradientColors = remember { image.colors.palette.map { Color(android.graphics.Color.parseColor(it)) } }

        Card(
            modifier = Modifier.fillMaxSize(0.8F),
            shape = RoundedCornerShape(12.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = gradientColors + Color.Black.copy(alpha = 0.5f),
                            radius = 6000f
                        )
                    )
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Card(shape = RoundedCornerShape(12.dp)) {
                    SubcomposeAsyncImage(
                        model = request,
                        contentDescription = "Wallpaper",
                        modifier = Modifier
                            .widthIn(max = 250.dp)
                            .heightIn(max = 350.dp),
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
                Text("Category: ${image.category}", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text("Tags: ${image.tags.joinToString(", ")}", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text("Rating: ${image.rating}", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text("Anime: ${image.anime.title ?: "Неизвестно"}", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text("Character: ${image.anime.character ?: "Неизвестно"}", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)


                Button(
                    onClick = onSetWallpaper,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.radialGradient(
                                    colors = gradientColors + Color.Black.copy(alpha = 0.5f),
                                    radius = 8000f
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Apply", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}