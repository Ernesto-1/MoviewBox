package com.exam.allmovies.presentation.wishlist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.allmovies.data.local.dao.DataWishListDao
import com.exam.allmovies.data.local.entitys.DataWishList
import com.exam.allmovies.data.remote.model.AMMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AMWishListViewModel @Inject constructor(private val repo: DataWishListDao) : ViewModel() {

    var state by mutableStateOf(AMWishListState())
        private set

    init {
        onEvent(AMWishListEvent.GetWishList)
    }

    fun onEvent(event: AMWishListEvent) {
        when (event) {
            is AMWishListEvent.GetWishList -> handleGetWishList()
        }
    }

    private fun handleGetWishList() {
        viewModelScope.launch {
            val existingPets = repo.getMovies().distinct()
            if (existingPets.isEmpty()) {
                updateState(wishList = mutableSetOf(), goOut = true)
            } else {
                updateState(wishList = existingPets.toMutableSet())
            }
        }
    }

    private fun updateState(
        loading: Boolean = state.loading,
        wishList: MutableSet<DataWishList> = state.wishList,
        goOut: Boolean = state.goOut
    ) {
        state = state.copy(
            loading = loading,
            wishList = wishList,
            goOut = goOut
        )
    }

}