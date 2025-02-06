package com.example.wallotaku.presentation.wallpaper_screen.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallotaku.presentation.theme.backgroundColor

@Composable
fun DrawerContent(){
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
            .background(color = backgroundColor)
            .padding(vertical = 25.dp)
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Text("WallOtaku", color = Color.White, fontWeight = FontWeight(600), fontSize = 35.sp, modifier = Modifier.padding(10.dp))
        }
    }
}