package com.exam.allmovies.ui.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.exam.allmovies.R
import com.exam.allmovies.data.local.Converters
import com.exam.allmovies.data.local.entitys.DataWishList
import com.exam.allmovies.presentation.detail.AMDetailEvent
import com.exam.allmovies.presentation.detail.AMDetailViewModel
import com.exam.allmovies.ui.components.AMButtonDefault
import com.exam.allmovies.ui.components.ChipOption
import com.exam.allmovies.ui.components.ListOptions
import com.exam.allmovies.ui.theme.bacgroudCard
import com.exam.allmovies.ui.theme.description
import com.exam.allmovies.ui.theme.grey100
import com.exam.allmovies.ui.utils.formatReleaseDate
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AMDetail(
    id: Int,
    fromWishList: Boolean = false,
    navController: NavController,
    onWishList: Boolean = false,
    viewModel: AMDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val scope = rememberCoroutineScope()
    state.isOnWishList.value = onWishList

    LaunchedEffect(key1 = Unit) {
        if (fromWishList) {
            viewModel.onEvent(AMDetailEvent.GetMovieFromWishList(id))
        } else {
            viewModel.onEvent(AMDetailEvent.GetDetailMovie(id.toString()))
        }
    }

    BackHandler {
        navController.previousBackStackEntry?.savedStateHandle?.set(
            "recharge",
            onWishList != state.isOnWishList.value
        )
        navController.navigateUp()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            navController.previousBackStackEntry?.savedStateHandle?.set(
                                "recharge",
                                onWishList != state.isOnWishList.value
                            )
                            navController.navigateUp()
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.am_ic_row_left),
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = bacgroudCard
                ),
                modifier = Modifier.height(50.dp),
                windowInsets = WindowInsets(top = 25.dp)
            )
        },
        containerColor = bacgroudCard,
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (state.loading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(40.dp), color = Color.White)
                }
            } else {
                Column {
                    Row(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                    ) {
                        AsyncImage(
                            model = state.data.posterPath,
                            contentDescription = "Image_pet_home",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .height(220.dp)
                                .border(border = BorderStroke(1.dp, grey100))
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            state.data.title,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 4.dp),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            formatReleaseDate(state.data.releaseDate),
                            color = description,
                            fontSize = 14.sp,
                            lineHeight = 10.sp
                        )
                        Text(
                            "duration: ${state.data.runtime} minutes",
                            color = description,
                            fontSize = 12.sp,
                            lineHeight = 10.sp
                        )
                    }

                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            stringResource(R.string.overview),
                            color = Color.White,
                            fontSize = 20.sp
                        )
                        Text(
                            state.data.overview,
                            color = description,
                            fontSize = 14.sp,
                            lineHeight = 16.sp
                        )
                        ListOptions(
                            list = state.listGenres
                        ) {
                            ChipOption(text = it)
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AMButtonDefault(
                        textButton = if (state.isOnWishList.value) stringResource(R.string.remove_wishlist) else stringResource(
                            R.string.add_wishlist
                        ),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(vertical = 12.dp),
                        radius = 16.dp
                    ) {
                        viewModel.onEvent(
                            AMDetailEvent.ChangeWishList(
                                DataWishList(
                                    id = state.data.id,
                                    title = state.data.title,
                                    posterPath = state.data.posterPath,
                                    releaseDate = state.data.releaseDate,
                                    overview = state.data.overview,
                                    runtime = state.data.runtime,
                                    genres = Converters().toStringList(state.listGenres),
                                    voteAverage = state.data.voteAverage,
                                    language = state.data.language
                                )
                            )
                        )

                    }
                }


            }
        }
    }
}