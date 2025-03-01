package com.exam.allmovies.data.local.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_wishlist")
data class DataWishList(
    @PrimaryKey
    val id: Int = 0,
    var genres: String = "",
    var overview: String = "",
    val language: String = "",
    var posterPath: String = "",
    var title: String = "",
    var runtime: Int = 0,
    val voteAverage: Double = 0.0,
    val releaseDate: String = ""
)

