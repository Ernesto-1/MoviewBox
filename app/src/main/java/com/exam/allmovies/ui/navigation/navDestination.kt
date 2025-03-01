package com.exam.allmovies.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
data class Detail(
    val id: Int,
    val inWishList: Boolean,
    val fromWishList: Boolean = false,
)

@Serializable
object Home

@Serializable
object WishList