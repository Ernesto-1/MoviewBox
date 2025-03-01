package com.exam.allmovies.domain.home

import com.exam.allmovies.data.remote.model.AMListDataClass

interface AMHomeRepo {

    suspend fun getMovies(page: Int): AMListDataClass?

    suspend fun getPopularMovies(page: Int): AMListDataClass?
}