package com.exam.allmovies.domain

import com.exam.allmovies.data.remote.model.AMDetailDataClass
import com.exam.allmovies.data.remote.model.AMListDataClass
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {

    @Headers("Content-Type: application/json")
    @GET("now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = "55957fcf3ba81b137f8fc01ac5a31fb5"
    ): AMListDataClass?

    @Headers("Content-Type: application/json")
    @GET("popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = "55957fcf3ba81b137f8fc01ac5a31fb5"
    ): AMListDataClass?

    @Headers("Content-Type: application/json")
    @GET("{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: String,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = "55957fcf3ba81b137f8fc01ac5a31fb5",
    ): AMDetailDataClass?

}