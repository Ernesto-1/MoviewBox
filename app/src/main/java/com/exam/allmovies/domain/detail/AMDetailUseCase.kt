package com.exam.allmovies.domain.detail

import com.exam.allmovies.data.remote.model.AMDetail
import com.exam.allmovies.domain.mapper.detail.mapToAMDetailMovie
import com.exam.allmovies.ui.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AMDetailUseCase @Inject constructor(private val repo: AMDetailRepo) {

    fun invoke(id: String): Flow<Resource<AMDetail>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(
                    Resource.Success(
                        repo.getDetailMovie(id)?.mapToAMDetailMovie() ?: AMDetail()
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
}