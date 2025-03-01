package com.exam.allmovies.data.remote.detail

import com.exam.allmovies.data.remote.model.AMDetailDataClass
import com.exam.allmovies.domain.WebService
import javax.inject.Inject

class AMDetailDataSource @Inject constructor(private val webService: WebService) {

    suspend fun getDetailMovie(id: String): AMDetailDataClass? {
        return webService.getDetailMovie(movieId = id)
    }
}