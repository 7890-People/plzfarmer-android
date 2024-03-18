package com.konkuk.plzfarmer.presentation.base

import android.util.Log
import com.konkuk.plzfarmer.utils.ResponseFailedException
import com.konkuk.plzfarmer.utils.network.ApiState
import retrofit2.Response

abstract class BaseRepository {
    protected suspend fun <T> makeRequest(call: suspend () -> Response<T>): ApiState<out T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                Log.d("Network", "API Succeed Response: $response")
                ApiState.Success(response.body())
            } else {
                ApiState.Error(ResponseFailedException(response.code()))
            }
        } catch (e: Exception) {
            Log.e("Network","Network Error 발생! ${e.stackTraceToString()}")
            ApiState.Error(ResponseFailedException(status = 1004))
        }
    }
}