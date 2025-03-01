package com.exam.allmovies.domain.mapper.detail

import com.exam.allmovies.data.remote.model.AMDetail
import com.exam.allmovies.data.remote.model.AMDetailDataClass
import com.exam.allmovies.data.remote.model.Genres
import com.exam.allmovies.ui.utils.AppConstants.URL_BASE_IMG

fun AMDetailDataClass.mapToAMDetailMovie(): AMDetail {

    return AMDetail(
        genres = this.genres.map {
            Genres(
                id = it?.id ?: 0,
                name = it?.name ?: ""
            )
        },
        id = this.id ?: 0,
        overview = this.overview ?: "",
        posterPath = URL_BASE_IMG + (this.posterPath ?: ""),
        language = this.language ?: "",
        releaseDate = this.releaseDate ?: "",
        title = this.title ?: "",
        runtime = this.runtime ?: 0,
        voteAverage = this.voteAverage ?: 0.0
    )

}