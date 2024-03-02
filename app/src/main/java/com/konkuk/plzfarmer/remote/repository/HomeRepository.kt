package com.konkuk.plzfarmer.remote.repository

import com.konkuk.plzfarmer.presentation.base.BaseRepository
import com.konkuk.plzfarmer.presentation.main.home.MyPlantVO
import com.konkuk.plzfarmer.presentation.main.home.RecentHistoryItemVO
import com.konkuk.plzfarmer.remote.api.HomeApi
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

    suspend fun getRecentHistoryList(): ApiState<List<RecentHistoryItemVO>> {
        return ApiState.Success(
            data = listOf(
                RecentHistoryItemVO(
                    plantType = "포도",
                    imgUrl = "http://www.newsam.co.kr/data/photos/20180729/art_15318011131465_9b3bf5.jpg",
                    date = "2024-03-03",
                    diseaseName = "탄저병"
                ),
                RecentHistoryItemVO(
                    plantType = "포도",
                    imgUrl = "http://www.newsam.co.kr/data/photos/20180729/art_15318011131465_9b3bf5.jpg",
                    date = "2024-03-04",
                    diseaseName = "탄저병"
                ),
                RecentHistoryItemVO(
                    plantType = "포도",
                    imgUrl = "http://www.newsam.co.kr/data/photos/20180729/art_15318011131465_9b3bf5.jpg",
                    date = "2024-03-05",
                    diseaseName = "탄저병"
                ),
                RecentHistoryItemVO(
                    plantType = "포도",
                    imgUrl = "https://www.dongbangagro.co.kr/static/base/images/crop2_06_01.png",
                    date = "2024-03-06",
                    diseaseName = "탄저병"
                ),
                RecentHistoryItemVO(
                    plantType = "포도",
                    imgUrl = "http://www.newsam.co.kr/data/photos/20180729/art_15318011131465_9b3bf5.jpg",
                    date = "2024-03-07",
                    diseaseName = "탄저병"
                ),
                RecentHistoryItemVO(
                    plantType = "포도",
                    imgUrl = "http://www.newsam.co.kr/data/photos/20180729/art_15318011131465_9b3bf5.jpg",
                    date = "2024-03-08",
                    diseaseName = "탄저병"
                )
            )
        )
    }
}