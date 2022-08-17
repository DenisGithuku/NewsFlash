package com.githukudenis.newsflash.common

sealed class NetworkResource<T>(val data: T? = null, val exception: Throwable? = null) {
   class Loading<T> : NetworkResource<T>()
   data class Success<T>(val output_data: T): NetworkResource<T>(data = output_data)
   class Error<T>(val error: Throwable): NetworkResource<T>(exception = error)
}
