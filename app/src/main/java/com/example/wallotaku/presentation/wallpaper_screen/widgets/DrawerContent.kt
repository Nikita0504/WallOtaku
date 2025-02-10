package com.example.wallotaku.presentation.wallpaper_screen.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallotaku.presentation.theme.backgroundColor
import com.example.wallotaku.presentation.theme.pacificoFontFamily

@Composable
fun DrawerContent(
    additionalTags: String,
    blacklistedTags: String,
    onAdditionalTags: () -> Unit,
    onBlacklistedTags: () -> Unit,
    onConfirm: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
            .background(color = backgroundColor)
            .padding(vertical = 25.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                "WallOtaku",
                color = Color.White,
                fontFamily = pacificoFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 35.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Additional Tags:",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontFamily = pacificoFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                )
                IconButton(onClick = onAdditionalTags) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add Tag", tint = Color.White)
                }
            }

            Text(
                additionalTags,
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 16.dp, top = 5.dp),
                fontFamily = pacificoFontFamily,
                fontWeight = FontWeight.SemiBold,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Blacklisted Tags:",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontFamily = pacificoFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                )
                IconButton(onClick = onBlacklistedTags) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add Blacklist", tint = Color.White)
                }
            }

            Text(
                blacklistedTags,
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 16.dp, top = 5.dp),
                fontFamily = pacificoFontFamily,
                fontWeight = FontWeight.SemiBold,
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .padding(10.dp)
            ) {
                Text(
                    "Confirm Selection",
                    fontFamily = pacificoFontFamily,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
