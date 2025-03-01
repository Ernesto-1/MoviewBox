package com.exam.allmovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.exam.allmovies.data.local.dao.DataWishListDao
import com.exam.allmovies.data.local.entitys.DataWishList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Database(
    entities = [DataWishList::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun getWishLis(): DataWishListDao
}

class Converters {
    @TypeConverter
    fun fromStringList(value: String?): List<String> {
        return value?.let {
            Gson().fromJson(it, object : TypeToken<List<String>>() {}.type)
        } ?: emptyList()
    }

    @TypeConverter
    fun toStringList(list: List<String>?): String {
        return Gson().toJson(list)
    }
}

