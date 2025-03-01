package com.exam.allmovies.domain.detail

import com.exam.allmovies.data.remote.model.AMDetailDataClass

interface AMDetailRepo {

    suspend fun getDetailMovie(id: String): AMDetailDataClass?

}