package com.exam.allmovies.presentation.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.allmovies.data.local.Converters
import com.exam.allmovies.data.local.dao.DataWishListDao
import com.exam.allmovies.data.local.entitys.DataWishList
import com.exam.allmovies.data.remote.model.AMDetail
import com.exam.allmovies.domain.detail.AMDetailUseCase
import com.exam.allmovies.ui.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AMDetailViewModel @Inject constructor(
    private val useCase: AMDetailUseCase,
    private val repo: DataWishListDao,
) : ViewModel() {

    var state by mutableStateOf(AMDetailState())
        private set

    fun onEvent(event: AMDetailEvent) {
        when (event) {
            is AMDetailEvent.GetDetailMovie -> handleGetDetail(id = event.id)
            is AMDetailEvent.ChangeWishList -> handleWishList(movie = event.movie)
            is AMDetailEvent.GetMovieFromWishList -> handleGetMovieFromWishList(id = event.id)
        }
    }

    private fun handleGetDetail(id: String) {
        viewModelScope.launch {
            useCase.invoke(id).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        updateState(loading = true)
                    }

                    is Resource.Success -> {
                        val listGenres = result.data.genres.map { it.name }.distinct()
                        updateState(
                            loading = false,
                            data = result.data,
                            listGenres = listGenres
                        )
                    }

                    is Resource.Failure -> {
                        updateState(loading = false)
                    }

                    else -> {}
                }

            }
        }
    }

    private fun handleWishList(movie: DataWishList) {
        viewModelScope.launch {
            val existingMovie = repo.getMoviesById(movie.id)

            if (existingMovie == null) { // Si no existe, agregarla
                repo.upsertDataMovies(movie)
                state.isOnWishList.value = true
            } else {
                repo.deleteMovie(movie.id)
                state.isOnWishList.value = false
            }
        }
    }

    private fun handleGetMovieFromWishList(id: Int) {
        viewModelScope.launch {
            val existingMovie = repo.getMoviesById(id)
            if (existingMovie != null) {
                val listGenres = Converters().fromStringList(existingMovie.genres)
                updateState(
                    loading = false,
                    data = AMDetail(
                        id = existingMovie.id,
                        title = existingMovie.title,
                        overview = existingMovie.overview,
                        posterPath = existingMovie.posterPath,
                        releaseDate = existingMovie.releaseDate,
                        runtime = existingMovie.runtime,
                        voteAverage = existingMovie.voteAverage
                    ),
                    listGenres = listGenres
                )
            }
        }
    }


    private fun updateState(
        loading: Boolean = state.loading,
        data: AMDetail = state.data,
        listGenres: List<String> = state.listGenres,
        isOnWishList: MutableState<Boolean> = state.isOnWishList
    ) {
        state = state.copy(
            loading = loading,
            data = data,
            listGenres = listGenres,
            isOnWishList = isOnWishList
        )
    }
}