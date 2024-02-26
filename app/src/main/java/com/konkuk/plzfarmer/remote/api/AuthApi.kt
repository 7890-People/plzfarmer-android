package com.konkuk.plzfarmer.remote.api

import com.konkuk.plzfarmer.remote.request.LoginRequest
import com.konkuk.plzfarmer.remote.request.SignUpRequest
import com.konkuk.plzfarmer.remote.response.DuplicateResponse
import com.konkuk.plzfarmer.remote.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("users/log-in")
    suspend fun requestLogin(@Body request: LoginRequest): Response<LoginResponse>

    @POST("users/nick-repetition-check")
    suspend fun checkDuplicate(@Body userNickname: String): Response<DuplicateResponse>

    @POST("/users/sign-up")
    suspend fun requestSignUp(@Body request: SignUpRequest): Response<Unit>
}