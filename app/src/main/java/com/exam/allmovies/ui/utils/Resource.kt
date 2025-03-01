package com.exam.allmovies.ui.utils

import java.lang.Exception

sealed class Resource<out T> {
    class Loading<out T>: Resource<T>()
    data class Success<out T>(val data: T): Resource<T>()
    data class Failure(val exception: Exception): Resource<Nothing>()
    data class NewFailure(val exception: Message = Message()): Resource<Nothing>()
}