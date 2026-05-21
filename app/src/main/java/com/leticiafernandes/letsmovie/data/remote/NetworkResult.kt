package com.leticiafernandes.letsmovie.data.remote

import retrofit2.HttpException
import java.io.IOException

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class HttpError(val code: Int, val message: String?) : NetworkResult<Nothing>()
    data class NetworkError(val throwable: Throwable) : NetworkResult<Nothing>()
}

inline fun <T> NetworkResult<T>.getOrElse(onError: (NetworkResult<Nothing>) -> Nothing): T = when (this) {
    is NetworkResult.Success -> data
    is NetworkResult.HttpError -> onError(this)
    is NetworkResult.NetworkError -> onError(this)
}

suspend fun <T> safeApiCall(call: suspend () -> T): NetworkResult<T> = try {
    NetworkResult.Success(call())
} catch (e: HttpException) {
    NetworkResult.HttpError(e.code(), e.message())
} catch (e: IOException) {
    NetworkResult.NetworkError(e)
} catch (e: Exception) {
    NetworkResult.NetworkError(e)
}
