package com.exam.allmovies.domain.mapper.home

import com.exam.allmovies.data.remote.model.AMListDataClass
import com.exam.allmovies.data.remote.model.AMListMovie
import com.exam.allmovies.data.remote.model.AMMovie
import com.exam.allmovies.ui.utils.AppConstants.URL_BASE_IMG

fun AMListDataClass.mapToAMListMovie(): AMListMovie {

    val movies = this.results.map {
        AMMovie(
            id = it?.id ?: 0,
            image = URL_BASE_IMG + (it?.image ?: ""),
            posterPath = URL_BASE_IMG + (it?.posterPath ?: ""),
            language = it?.language ?: "",
            overview = it?.overview ?: "",
            voteAverage = it?.voteAverage ?: 0.0,
            title = it?.title ?: "",
            releaseDate = it?.releaseDate ?: ""
        )
    }

    return AMListMovie(
        listMovies = movies,
        page = this.page ?: 0
    )

}