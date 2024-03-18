package com.konkuk.plzfarmer.remote.response

data class FarmInfoResponse(
    val lon: Double, //Longitude of the location
    val lat: Double, //Latitude of the location
    val farmName: String // 농장 이름
)