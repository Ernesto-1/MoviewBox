package com.exam.allmovies.presentation.wishlist

import com.exam.allmovies.data.local.entitys.DataWishList

data class AMWishListState(
    val loading: Boolean = false,
    val wishList: MutableSet<DataWishList> = mutableSetOf(),
    val goOut: Boolean = false,
    )
