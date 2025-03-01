package com.exam.allmovies.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.exam.allmovies.data.remote.model.AMMovie
import com.exam.allmovies.ui.theme.bacgroudCard
import com.exam.allmovies.ui.theme.backgroudHome
import com.exam.allmovies.ui.theme.description
import com.exam.allmovies.ui.theme.green300
import com.exam.allmovies.ui.theme.textHome
import com.exam.allmovies.ui.utils.formatReleaseDate

@Composable
fun AMCardPopular(data: AMMovie, onCardClick: () -> Unit) {
    val progress = (data.voteAverage / 10).toFloat() // Convertir a escala 0.0 - 1.0
    val percentage = (progress * 100).toInt() // Convertir a porcentaje (0 - 100)

    Card(
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier
            .clickable { onCardClick.invoke() }
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = bacgroudCard)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = data.posterPath,
                contentDescription = "Image_pet_home",
                contentScale = ContentScale.Fit,
                modifier = Modifier.height(60.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    data.title,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 4.dp),
                    fontSize = 14.sp
                )
                Column {
                    Text(
                        formatReleaseDate(data.releaseDate),
                        color = description,
                        fontSize = 12.sp,
                        lineHeight = 10.sp
                    )
                    Text(
                        data.language.uppercase(),
                        fontSize = 12.sp,
                        color = description,
                        lineHeight = 10.sp
                    )
                }
            }
            Box(modifier = Modifier.size(60.dp)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        progress = { progress },
                        strokeWidth = 8.dp,
                        strokeCap = StrokeCap.Butt,
                        modifier = Modifier.size(150.dp),
                        trackColor = backgroudHome,
                        color = if (progress <= 0.5) textHome else green300
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "$percentage%", // Mostramos el porcentaje calculado
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        color = Color.White,
                    )

                }

            }
        }
        HorizontalDivider(thickness = 2.dp, color = backgroudHome)
    }
}