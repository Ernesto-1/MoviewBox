package com.exam.allmovies.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun ListOptions(list: List<String>, composable: @Composable (String) -> Unit = {}) {
    FlexLayout(verticalGap = 12.dp, horizontalGap = 8.dp) {
        list.forEach { options ->
            composable(options)
        }
    }
}