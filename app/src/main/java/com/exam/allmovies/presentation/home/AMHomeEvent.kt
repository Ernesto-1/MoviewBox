package com.exam.allmovies.presentation.home

sealed class AMHomeEvent {
    data class GetMovies(val page: Int) : AMHomeEvent()
    data class GetPopularMovies(val page: Int) : AMHomeEvent()
    data object GetWishList : AMHomeEvent()
}