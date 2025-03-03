package com.exam.allmovies.ui.wishlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.exam.allmovies.R
import com.exam.allmovies.data.remote.model.AMMovie
import com.exam.allmovies.presentation.wishlist.AMWishListEvent
import com.exam.allmovies.presentation.wishlist.AMWishListViewModel
import com.exam.allmovies.ui.components.AMCardPopular
import com.exam.allmovies.ui.theme.bacgroudCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AMWishList(
    navController: NavController,
    navigateDetail: (Int, Boolean) -> Unit = { _, _ -> },
    viewModel: AMWishListViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val recharge =
        navController.currentBackStackEntryAsState().value?.savedStateHandle?.get<Boolean>("recharge")
            ?: false

    LaunchedEffect(recharge) {
        if (recharge) {
            navController.previousBackStackEntry?.savedStateHandle?.set(
                "recharge",
                true
            )
            viewModel.onEvent(AMWishListEvent.GetWishList)
        }
    }

    LaunchedEffect(state.goOut) {
        if (state.goOut) {
            navController.previousBackStackEntry?.savedStateHandle?.set(
                "recharge",
                true
            )
            navController.navigateUp()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.wishList), color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
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
                modifier = Modifier.height(60.dp),
                windowInsets = WindowInsets(top = 25.dp)
            )
        },
        containerColor = bacgroudCard,
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateBottomPadding()),
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(state.wishList.toList()) { popular ->
                    AMCardPopular(
                        data =
                        AMMovie(
                            posterPath = popular.posterPath,
                            title = popular.title,
                            releaseDate = popular.releaseDate,
                            voteAverage = popular.voteAverage,
                            id = popular.id,
                            overview = popular.overview,
                            language = popular.language,
                        )
                    ) {
                        navigateDetail.invoke(
                            popular.id,
                            state.wishList.any { it.id == popular.id })
                    }
                }
            }
        }
    }
}