package com.konkuk.plzfarmer.remote.repository

import com.konkuk.plzfarmer.presentation.base.BaseRepository
import com.konkuk.plzfarmer.presentation.main.home.MyPlantVO
import com.konkuk.plzfarmer.remote.api.HomeApi
import com.konkuk.plzfarmer.remote.response.FarmInfoResponse
import com.konkuk.plzfarmer.utils.network.ApiState
import com.konkuk.plzfarmer.utils.network.RetrofitClient

class HomeRepository: BaseRepository() {
    val api = RetrofitClient.create(HomeApi::class.java)
    suspend fun getMyPlantList(): ApiState<List<MyPlantVO>> {
        return ApiState.Success(
            data = listOf(
                MyPlantVO(
                    plantTypeKor = "포도",
                    plantTypeEng = "Graph",
                    imgUrl = "이미지 url"
                )
            )
        )
    }
}