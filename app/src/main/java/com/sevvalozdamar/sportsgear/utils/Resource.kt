package com.sevvalozdamar.sportsgear.utils

sealed class Resource<out T: Any>{
    data class Success<out T: Any>(val data: T): Resource<T>()
    data class Error(val errorMessage: String): Resource<Nothing>()
    data class Fail(val failMessage: String): Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}
