package com.exam.allmovies.data.remote.model

import com.google.gson.annotations.SerializedName

data class AMDetailDataClass(
    @SerializedName("genres") var genres: List<GenresDataClass?> = listOf(),
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("overview") var overview: String? = "",
    @SerializedName("poster_path") var posterPath: String? = "",
    @SerializedName("release_date") var releaseDate: String? = "",
    @SerializedName("title") var title: String? = "",
    @SerializedName("runtime") var runtime: Int? = 0,
    @SerializedName("original_language") var language: String? = "",
    @SerializedName("vote_average") var voteAverage: Double? = 0.0,
    )

data class GenresDataClass(
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("name") var name: String? = ""
)

data class AMDetail(
    var genres: List<Genres> = listOf(),
    var id: Int = 0,
    var overview: String = "",
    var posterPath: String = "",
    var releaseDate: String = "",
    var title: String = "",
    var runtime: Int = 0,
    var language: String = "",
    var voteAverage: Double = 0.0,

)

data class Genres(
    var id: Int = 0,
    var name: String = "",
)


