package com.exam.allmovies.data.remote.model

import com.google.gson.annotations.SerializedName

data class AMListDataClass(
    @SerializedName("results") var results: List<Results?> = listOf(),
    @SerializedName("page") var page: Int? = 0
    )

data class Results(
    @SerializedName("backdrop_path") var image: String? = "",
    @SerializedName("poster_path") var posterPath: String? = "",
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("original_language") var language: String? = "",
    @SerializedName("original_title") var title: String? = "",
    @SerializedName("overview") var overview: String? = "",
    @SerializedName("vote_average") var voteAverage: Double? = 0.0,
    @SerializedName("release_date") var releaseDate: String? = ""
)

data class AMListMovie(
    val listMovies: List<AMMovie> = listOf(),
    val page: Int = 0
)

data class AMMovie(
    val image: String = "",
    val posterPath: String = "",
    val id: Int = 0,
    val language: String = "",
    val title: String = "",
    val overview: String = "",
    val voteAverage: Double = 0.0,
    val releaseDate: String = ""
)



