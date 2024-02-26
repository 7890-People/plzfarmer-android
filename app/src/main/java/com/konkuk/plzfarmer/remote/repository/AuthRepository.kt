package com.konkuk.plzfarmer.remote.repository

import com.konkuk.plzfarmer.presentation.base.BaseRepository
import com.konkuk.plzfarmer.remote.api.AuthApi
import com.konkuk.plzfarmer.remote.response.DuplicateResponse
import com.konkuk.plzfarmer.remote.response.LoginResponse
import com.konkuk.plzfarmer.utils.ResponseFailedException
import com.konkuk.plzfarmer.utils.network.ApiState
import com.konkuk.plzfarmer.utils.network.RetrofitClient
import retrofit2.Retrofit

class AuthRepository : BaseRepository() {

    val api = RetrofitClient.create(AuthApi::class.java)
//    suspend fun requestLogin(id: String): ApiState<out LoginResponse> = makeRequest {
//        api.requestLogin(id)
//    }
//
//    suspend fun checkDuplicate(nickname:String): ApiState<out DuplicateResponse> = makeRequest {
//        api.checkDuplicate(nickname)
//    }

    suspend fun requestLogin(id: String): ApiState<out LoginResponse>{
        return ApiState.Error(ResponseFailedException(status = 404))
    }

    suspend fun checkDuplicate(nickname:String): ApiState<out DuplicateResponse>  {
        return ApiState.Success(DuplicateResponse(true))
    }
}