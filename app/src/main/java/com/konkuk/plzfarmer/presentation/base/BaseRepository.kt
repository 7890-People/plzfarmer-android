package com.konkuk.plzfarmer.presentation.base

import android.util.Log
import com.konkuk.plzfarmer.utils.ResponseFailedException
import com.konkuk.plzfarmer.utils.network.ApiState
import org.json.JSONObject
import retrofit2.Response

abstract class BaseRepository {
    protected suspend fun <T> makeRequest(call: suspend () -> Response<T>): ApiState<out T> {
        val response = call()
        if (response.isSuccessful) {
            Log.d("Network", "API Succeed Response: $response")
            return ApiState.Success(response.body())
        } else {
            if (response.errorBody() == null) return ApiState.Error(ResponseFailedException(1004))

            val errorBody = response.errorBody()!!.string()
            return ApiState.Error(ResponseFailedException(JSONObject(errorBody).getInt("status")))
        }
    }

}