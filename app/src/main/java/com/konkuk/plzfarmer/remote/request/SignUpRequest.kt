package com.konkuk.plzfarmer.remote.request

data class SignUpRequest(
    val userNickname: String? = null,
    val userId: String,
    val userEmail: String,
    val userProfile: String? = "https://i.pinimg.com/originals/c6/ca/3e/c6ca3e06118176239fa3d0253b4b5de8.png",
    val farmName: String? = null,
    val farmAddress: String? = null,
    val isFarmIndoor: Boolean? = null
)