package com.exam.allmovies.presentation.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.allmovies.data.local.dao.DataWishListDao
import com.exam.allmovies.data.remote.model.AMMovie
import com.exam.allmovies.domain.home.AMHomeUseCase
import com.exam.allmovies.ui.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AMHomeViewModel @Inject constructor(
    private val useCase: AMHomeUseCase,
    private val repo: DataWishListDao
) : ViewModel() {

    var state by mutableStateOf(AMHomeState())
        private set

    init {
        onEvent(AMHomeEvent.GetWishList)
        onEvent(AMHomeEvent.GetMovies(state.pageNumbers.value))
        onEvent(AMHomeEvent.GetPopularMovies(state.pageNumbers.value))
    }

    fun onEvent(event: AMHomeEvent) {
        when (event) {
            is AMHomeEvent.GetMovies -> {
                handleGetMovies(event.page)
            }

            is AMHomeEvent.GetPopularMovies -> {
                handlePopularMovies(event.page)
            }
            is AMHomeEvent.GetWishList -> {
                handleGetWishList()
            }
        }
    }

    fun handleGetMovies(page: Int) {
        viewModelScope.launch {
            useCase.getMovies(page).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        updateState(loading = true)
                    }

                    is Resource.Success -> {
                        updateState(
                            loading = false,
                            dataMovies = result.data.listMovies.toMutableSet()
                        )
                        Log.d("uvbiunim", result.data.listMovies.toString())
                    }

                    is Resource.Failure -> {
                        updateState(loading = false)
                    }

                    is Resource.NewFailure -> TODO()
                }
            }
        }
    }

    private fun handlePopularMovies(page: Int) {
        viewModelScope.launch {
            useCase.getPopularMovies(page).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        updateState(loading = true)
                    }

                    is Resource.Success -> {
                        val popularMovies = state.dataPopularMovies + result.data.listMovies
                        updateState(
                            loading = false,
                            dataPopularMovies = popularMovies.toMutableSet()
                        )
                    }

                    is Resource.Failure -> {
                        updateState(loading = false)
                    }

                    is Resource.NewFailure -> TODO()
                }

            }
        }
    }

    private fun handleGetWishList() {
        viewModelScope.launch {
            val existingPets = repo.getMovies().map { it.id }.distinct()
            if (existingPets.isEmpty()) {
                updateState(wishList = mutableSetOf())
            } else {
                updateState(wishList = existingPets.toMutableSet())
            }
        }
    }

    private fun updateState(
        loading: Boolean = state.loading,
        dataMovies: MutableSet<AMMovie> = state.dataMovies,
        dataPopularMovies: MutableSet<AMMovie> = state.dataPopularMovies,
        pageNumber: MutableState<Int> = state.pageNumbers,
        wishList: MutableSet<Int> = state.wishList,
    ) {
        state = state.copy(
            loading = loading,
            dataMovies = dataMovies,
            dataPopularMovies = dataPopularMovies,
            pageNumbers = pageNumber,
            wishList = wishList
        )
    }

}