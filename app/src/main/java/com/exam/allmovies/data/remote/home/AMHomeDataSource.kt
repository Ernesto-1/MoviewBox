package com.exam.allmovies.data.remote.home

import com.exam.allmovies.data.remote.model.AMListDataClass
import com.exam.allmovies.domain.WebService
import javax.inject.Inject

class AMHomeDataSource @Inject constructor(private val webService: WebService) {

    suspend fun getMovies(page: Int): AMListDataClass? {
        return webService.getNowPlayingMovies(
            page = page
        )
    }

    suspend fun getPopularMovies(page: Int): AMListDataClass? {
        return webService.getPopularMovies(
            page = page
        )
    }

}