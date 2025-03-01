package com.exam.allmovies.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChipOption(text: String) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Text(
            text,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            fontSize = 14.sp
        )
    }
}