package com.exam.allmovies.presentation.wishlist

sealed class AMWishListEvent {
    data object GetWishList: AMWishListEvent()
}