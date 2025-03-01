package com.exam.allmovies.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.exam.allmovies.data.local.entitys.DataWishList

@Dao
interface DataWishListDao {

    @Query("DELETE FROM data_wishlist WHERE id = :idMovie")
    suspend fun deleteMovie(idMovie: Int)

    @Query("SELECT * FROM data_wishlist")
    suspend fun getMovies(): List<DataWishList>

    @Query("SELECT * FROM data_wishlist WHERE id = :idMovie")
    suspend fun getMoviesById(idMovie: Int): DataWishList?

    @Upsert
    suspend fun upsertDataMovies(dataModel: DataWishList)


}