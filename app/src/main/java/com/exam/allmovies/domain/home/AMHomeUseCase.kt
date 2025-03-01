package com.exam.allmovies.domain.home

import com.exam.allmovies.data.remote.model.AMListMovie
import com.exam.allmovies.domain.mapper.home.mapToAMListMovie
import com.exam.allmovies.ui.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AMHomeUseCase @Inject constructor(private val repo: AMHomeRepo) {

    fun getMovies(page: Int): Flow<Resource<AMListMovie>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(
                    Resource.Success(
                        repo.getMovies(page = page)?.mapToAMListMovie() ?: AMListMovie()
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }.flowOn(Dispatchers.IO)

    fun getPopularMovies(page: Int): Flow<Resource<AMListMovie>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(
                    Resource.Success(
                        repo.getMovies(page = page)?.mapToAMListMovie() ?: AMListMovie()
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
}