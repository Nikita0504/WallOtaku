package com.example.wallotaku.presentation.wallpaper_screen.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.wallotaku.presentation.theme.backgroundColor

@Composable
fun CategoriesDialogWindow(
    categories: List<String>,
    selectedCategories: MutableState<Set<String>>,
    onSave: (Set<String>) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier.width(300.dp).height(450.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { category ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = category in selectedCategories.value,
                                onCheckedChange = { isChecked ->
                                    selectedCategories.value = if (isChecked) {
                                        selectedCategories.value + category
                                    } else {
                                        selectedCategories.value - category
                                    }
                                }
                            )
                            Text(category, color = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    onSave(selectedCategories.value)
                }) {
                    Text("Сохранить")
                }
            }
        }
    }
}
