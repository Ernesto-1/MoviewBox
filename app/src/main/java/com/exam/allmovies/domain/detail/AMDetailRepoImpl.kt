package com.exam.allmovies.domain.detail

import com.exam.allmovies.data.remote.detail.AMDetailDataSource
import com.exam.allmovies.data.remote.model.AMDetailDataClass
import javax.inject.Inject

class AMDetailRepoImpl @Inject constructor(private val dataSource: AMDetailDataSource) : AMDetailRepo {

    override suspend fun getDetailMovie(id: String): AMDetailDataClass? = dataSource.getDetailMovie(id)
}