package com.exam.allmovies.presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import com.exam.allmovies.data.remote.model.AMMovie

data class AMHomeState(
    val loading: Boolean = false,
    val dataMovies: MutableSet<AMMovie> = mutableSetOf(),
    val dataPopularMovies: MutableSet<AMMovie> = mutableSetOf(),
    val pageNumbers: MutableState<Int> = mutableIntStateOf(1),
    val wishList: MutableSet<Int> = mutableSetOf(),

    )
