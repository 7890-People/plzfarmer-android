package com.konkuk.plzfarmer.remote.api

import com.konkuk.plzfarmer.remote.response.DuplicateResponse
import com.konkuk.plzfarmer.remote.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("members/login")
    fun requestLogin(@Body userId: String): Response<LoginResponse>

    @POST("members/nick-repetition-check")
    fun checkDuplicate(@Body userNickname: String): Response<DuplicateResponse>
}