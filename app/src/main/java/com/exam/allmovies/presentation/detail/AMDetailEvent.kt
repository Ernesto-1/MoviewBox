package com.exam.allmovies.presentation.detail

import com.exam.allmovies.data.local.entitys.DataWishList

sealed class AMDetailEvent {

    data class GetDetailMovie(val id: String) : AMDetailEvent()
    data class GetMovieFromWishList(val id: Int) : AMDetailEvent()
    data class ChangeWishList(val movie: DataWishList) : AMDetailEvent()

}