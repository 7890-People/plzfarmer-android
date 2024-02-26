package com.konkuk.plzfarmer.remote.api

import com.konkuk.plzfarmer.remote.request.SignUpRequest
import com.konkuk.plzfarmer.remote.response.DuplicateResponse
import com.konkuk.plzfarmer.remote.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthApi {

    @POST("members/login")
    suspend fun requestLogin(@Body userId: String): Response<LoginResponse>

    @POST("members/nick-repetition-check")
    suspend fun checkDuplicate(@Body userNickname: String): Response<DuplicateResponse>

    @POST("/users/sign-up")
    suspend fun requestSignUp(@Body request: SignUpRequest): Response<Unit>
}