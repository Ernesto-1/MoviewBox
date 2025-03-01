package com.exam.allmovies.domain.home

import com.exam.allmovies.data.remote.home.AMHomeDataSource
import com.exam.allmovies.data.remote.model.AMListDataClass
import javax.inject.Inject

class AMHomeRepoImpl @Inject constructor(private val dataSource: AMHomeDataSource) : AMHomeRepo {
    override suspend fun getMovies(page: Int): AMListDataClass? = dataSource.getMovies(page)
    override suspend fun getPopularMovies(page: Int): AMListDataClass? = dataSource.getPopularMovies(page)
}