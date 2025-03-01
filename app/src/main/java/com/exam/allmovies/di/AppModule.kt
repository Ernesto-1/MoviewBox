package com.exam.allmovies.di

import android.content.Context
import androidx.room.Room
import com.exam.allmovies.data.local.RoomDataBase
import com.exam.allmovies.data.local.dao.DataWishListDao
import com.exam.allmovies.domain.WebService
import com.exam.allmovies.domain.detail.AMDetailRepo
import com.exam.allmovies.domain.detail.AMDetailRepoImpl
import com.exam.allmovies.domain.home.AMHomeRepo
import com.exam.allmovies.domain.home.AMHomeRepoImpl
import com.exam.allmovies.ui.utils.AppConstants.BASE_URL
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun providesHomeRepository(repoHome: AMHomeRepoImpl): AMHomeRepo
    @Binds
    abstract fun providesDetailRepository(repoDetail: AMDetailRepoImpl): AMDetailRepo

    companion object {

        @Provides
        fun providesOkHttpClient(@ApplicationContext context: Context): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().also {
                        it.setLevel(
                            HttpLoggingInterceptor.Level.HEADERS
                        )
                    }
                )
                .build()

        @Provides
        fun providesRetrofitInstance(client: OkHttpClient): Retrofit {
            val gson = GsonBuilder().setLenient().create()
            return Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        @Provides
        fun providesMapsService(retrofit: Retrofit) =
            retrofit.create<WebService>()

        @Provides
        fun provideWishListRoomDao(appDatabase: RoomDataBase): DataWishListDao {
            return appDatabase.getWishLis()
        }

        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context) : RoomDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                RoomDataBase::class.java,
                "WishList_database"
            ).fallbackToDestructiveMigration()
                .allowMainThreadQueries().build()
        }
    }
}