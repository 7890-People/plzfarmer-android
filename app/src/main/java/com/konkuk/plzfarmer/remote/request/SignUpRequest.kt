package com.konkuk.plzfarmer.remote.request

data class SignUpRequest(
    val userNickname: String? = null,
    val userId: String,
    val userEmail: String,
    val userProfile: String? = null,
    val farmName: String? = null,
    val farmAddress: String? = null,
    val isFarmIndoor: Boolean? = null
)