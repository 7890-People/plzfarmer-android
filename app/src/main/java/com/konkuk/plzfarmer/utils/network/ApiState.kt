package com.konkuk.plzfarmer.utils.network

import com.konkuk.plzfarmer.utils.ResponseFailedException

sealed class ApiState<T> {
    data class Success<T>(val data: T?, val message: String? = null) : ApiState<T>()
    data class Error(val exception: ResponseFailedException) : ApiState<Nothing>()
    object Loading : ApiState<Nothing>()

    fun <R> byState(
        onSuccess: (T) -> (R?),
        onError: (exception: ResponseFailedException) -> (Unit) = {},
        onLoading: () -> (Unit) = {}
    ): R? {
        when (this) {
            is Success -> {
                return this.data?.let(onSuccess)
            }

            is Error -> {
                onError(exception)
            }

            is Loading -> {
                onLoading()
            }
        }
        return null
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            is Loading -> "Loading"
        }
    }

}