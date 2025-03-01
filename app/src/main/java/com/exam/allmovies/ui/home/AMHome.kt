package com.exam.allmovies.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.exam.allmovies.R
import com.exam.allmovies.presentation.home.AMHomeEvent
import com.exam.allmovies.presentation.home.AMHomeViewModel
import com.exam.allmovies.ui.components.AMCard
import com.exam.allmovies.ui.components.AMCardPopular
import com.exam.allmovies.ui.navigation.WishList
import com.exam.allmovies.ui.theme.bacgroudCard
import com.exam.allmovies.ui.theme.backgroudHome
import com.exam.allmovies.ui.theme.textHome
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun AMHome(
    navController: NavController,
    viewModel: AMHomeViewModel = hiltViewModel(),
    navigateDetail: (Int, Boolean) -> Unit = { _, _ -> }
) {
    val state = viewModel.state
    val lazyListState = rememberLazyListState()
    val recharge =
        navController.currentBackStackEntryAsState().value?.savedStateHandle?.get<Boolean>("recharge")
            ?: false

    LaunchedEffect(recharge) {
        if (recharge) {
            viewModel.onEvent(AMHomeEvent.GetWishList)
        }
    }

    // Detectar cuando llegamos al final de la lista
    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.layoutInfo }
            .map { layoutInfo ->
                val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                val totalItemsCount = layoutInfo.totalItemsCount
                lastVisibleItemIndex >= totalItemsCount - 4 // Se activará cuando queden 4 elementos
            }
            .distinctUntilChanged() // Evita llamadas duplicadas
            .collect { shouldLoadMore ->
                if (shouldLoadMore && !state.loading) {
                    state.pageNumbers.value++
                    Log.d("PAGINACIÓN", "Cargando página: ${state.pageNumbers.value}")
                    viewModel.onEvent(AMHomeEvent.GetPopularMovies(state.pageNumbers.value))
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            colors = CardDefaults.cardColors(containerColor = bacgroudCard),
            shape = RoundedCornerShape(0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = if (state.wishList.isNotEmpty()) Arrangement.SpaceBetween else Arrangement.Center
            ) {
                Text(
                    "MOVIEBOX",
                    fontSize = 35.sp,
                    color = textHome,
                    fontFamily = FontFamily(Font(R.font.bebasneue_regular, FontWeight.Bold))
                )
                if (state.wishList.isNotEmpty()) {
                    TextButton(onClick = {
                        navController.navigate(
                            WishList
                        )
                    }) {
                        Text(
                            stringResource(R.string.wishList),
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }

            }
        }
        AMTitle("Playing now")
        LazyRow {
            items(state.dataMovies.toList()) { movie ->
                AMCard(image = movie.posterPath)
            }
        }

        AMTitle("Most popular")
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(state.dataPopularMovies.toList()) { popular ->
                AMCardPopular(data = popular) {
                    navigateDetail.invoke(popular.id, state.wishList.any { it == popular.id })
                }
            }
        }
    }


}

@Composable
fun AMTitle(title: String) {
    Column(modifier = Modifier.background(backgroudHome)) {
        Text(
            text = title,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            color = textHome,
            fontSize = 14.sp
        )
    }
}
