package com.exam.allmovies.presentation.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.exam.allmovies.data.remote.model.AMDetail

data class AMDetailState(
    val loading: Boolean = false,
    val data: AMDetail = AMDetail(),
    val listGenres: List<String> = listOf(),
    val isOnWishList: MutableState<Boolean> = mutableStateOf(false),
)
