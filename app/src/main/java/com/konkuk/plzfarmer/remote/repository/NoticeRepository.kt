package com.konkuk.plzfarmer.remote.repository

import com.konkuk.plzfarmer.presentation.base.BaseRepository
import com.konkuk.plzfarmer.presentation.main.notice.NoticeVO
import com.konkuk.plzfarmer.remote.api.NoticeApi
import com.konkuk.plzfarmer.utils.network.ApiState
import com.konkuk.plzfarmer.utils.network.RetrofitClient

class NoticeRepository: BaseRepository() {
    val api = RetrofitClient.create(NoticeApi::class.java)

    suspend fun getNoticeList(): ApiState<List<NoticeVO>> {
        return ApiState.Success(
            data = listOf(
                NoticeVO(
                    noticeId = 1,
                    noticeType = "댓글",
                    noticeContent1 = "새로운 댓글이 달렸어요 : 오 저도 그래요",
                    noticeContent2 = "나의 게시글 : 우리집 포도에 이상한 증상이 생겼어요",
                ),
                NoticeVO(
                    noticeId = 2,
                    noticeType = "댓글",
                    noticeContent1 = "새로운 댓글이 달렸어요 : 오 저도 그래요",
                    noticeContent2 = "나의 댓글 : 우리집 포도에 이상한 증상이 생겼어요",
                ),
                NoticeVO(
                    noticeId = 3,
                    noticeType = "좋아요",
                    noticeContent1 = "다음 게시글에 추천이 달렸어요",
                    noticeContent2 = "우리집 포도에 이상한 증상이 생겼어요 어떡해야할까요??",
                )
            )
        )
    }

}