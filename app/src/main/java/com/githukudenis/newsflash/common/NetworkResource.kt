package com.githukudenis.newsflash.common

data class NetworkResource<T>(val data: T? = null, val exception: Throwable? = null) {
    fun <T> loading(): NetworkResource<T> {
        return NetworkResource()
    }

    fun <T> success(data: T): NetworkResource<T> {
        return NetworkResource(data = data)
    }

    fun <T> error( message: Throwable?): NetworkResource<T> {
        return NetworkResource(exception = message)
    }
}
