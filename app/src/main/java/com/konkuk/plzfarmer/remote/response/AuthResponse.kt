package com.konkuk.plzfarmer.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token") val accessToken: String,
)

data class DuplicateResponse(
    val canUse: Boolean
)