package com.exam.allmovies.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exam.allmovies.ui.theme.backGroud
import com.exam.allmovies.ui.theme.green400

@Composable
fun AMButtonDefault(
    modifier: Modifier = Modifier,
    textButton: String? = "textBtn",
    enabled: Boolean = true,
    textSize: Int = 14,
    radius: Dp = 0.dp,
    colorBackground: Color = green400,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick()
        },
        enabled = enabled,
        elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) colorBackground else backGroud
        ), modifier = modifier, shape = RoundedCornerShape(radius)
    ) {
        Text(
            text = textButton ?: "", fontSize = textSize.sp, fontWeight = FontWeight.Bold
        )
    }
}