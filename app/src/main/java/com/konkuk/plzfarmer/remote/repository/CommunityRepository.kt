package com.konkuk.plzfarmer.remote.repository

import com.konkuk.plzfarmer.presentation.base.BaseRepository
import com.konkuk.plzfarmer.presentation.main.community.PostVO
import com.konkuk.plzfarmer.remote.api.CommunityApi
import com.konkuk.plzfarmer.utils.network.ApiState
import com.konkuk.plzfarmer.utils.network.RetrofitClient

class CommunityRepository: BaseRepository() {
    val api = RetrofitClient.create(CommunityApi::class.java)

    suspend fun getPostList(filter: String): ApiState<List<PostVO>> {
        if (filter == "전체"){
            return ApiState.Success(
                data = listOf(
                    PostVO(
                        postId = 1,
                        postType = "질문",
                        postTitle = "우리집 포도에 이상한  증상이 생겼어요오오오이이잉",
                        postMemberName = "거꾸로 해도 토마토",
                        postDate = "2023-10-09",
                        likeCount = 10,
                        commentCount = 18,
                        postImage = "http://www.newsam.co.kr/data/photos/20180729/art_15318011131465_9b3bf5.jpg"
                    ),
                    PostVO(
                        postId = 2,
                        postType = "잡담",
                        postTitle = "우리집 포도에 이상한  증상이 생겼어요오오오이이잉",
                        postMemberName = "거꾸로 해도 토마토",
                        postDate = "2023-10-10",
                        likeCount = 11,
                        commentCount = 18,
                        postImage = null
                    ),
                    PostVO(
                        postId = 1,
                        postType = "질문",
                        postTitle = "우리집 포도에 이상한  증상이 생겼어요오오오이이잉",
                        postMemberName = "거꾸로 해도 토마토",
                        postDate = "2023-10-11",
                        likeCount = 10,
                        commentCount = 18,
                        postImage = null
                    ),
                    PostVO(
                        postId = 2,
                        postType = "잡담",
                        postTitle = "우리집 포도에 이상한  증상이 생겼어요오오오이이잉",
                        postMemberName = "거꾸로 해도 토마토",
                        postDate = "2023-10-12",
                        likeCount = 11,
                        commentCount = 18,
                        postImage = "http://www.newsam.co.kr/data/photos/20180729/art_15318011131465_9b3bf5.jpg"
                    ),
                    PostVO(
                        postId = 1,
                        postType = "질문",
                        postTitle = "우리집 포도에 이상한  증상이 생겼어요오오오이이잉",
                        postMemberName = "거꾸로 해도 토마토",
                        postDate = "2023-10-13",
                        likeCount = 10,
                        commentCount = 18,
                        postImage = null
                    ),
                    PostVO(
                        postId = 2,
                        postType = "잡담",
                        postTitle = "우리집 포도에 이상한  증상이 생겼어요오오오이이잉",
                        postMemberName = "거꾸로 해도 토마토",
                        postDate = "2023-10-14",
                        likeCount = 11,
                        commentCount = 18,
                        postImage = "http://www.newsam.co.kr/data/photos/20180729/art_15318011131465_9b3bf5.jpg"
                    ),
                )
            )
        }else if (filter == "잡담"){
            return ApiState.Success(
                data = listOf(
                    PostVO(
                        postId = 2,
                        postType = "잡담",
                        postTitle = "우리집 포도에 이상한  증상이 생겼어요오오오이이잉",
                        postMemberName = "거꾸로 해도 토마토",
                        postDate = "2023-10-10",
                        likeCount = 11,
                        commentCount = 18,
                        postImage = null
                    ),
                    PostVO(
                        postId = 2,
                        postType = "잡담",
                        postTitle = "우리집 포도에 이상한  증상이 생겼어요오오오이이잉",
                        postMemberName = "거꾸로 해도 토마토",
                        postDate = "2023-10-12",
                        likeCount = 11,
                        commentCount = 18,
                        postImage = "http://www.newsam.co.kr/data/photos/20180729/art_15318011131465_9b3bf5.jpg"
                    ),
                    PostVO(
                        postId = 2,
                        postType = "잡담",
                        postTitle = "우리집 포도에 이상한  증상이 생겼어요오오오이이잉",
                        postMemberName = "거꾸로 해도 토마토",
                        postDate = "2023-10-14",
                        likeCount = 11,
                        commentCount = 18,
                        postImage = "http://www.newsam.co.kr/data/photos/20180729/art_15318011131465_9b3bf5.jpg"
                    ),
                )
            )
        }else{ //(filter == "질문")
            return ApiState.Success(
                data = listOf(
                    PostVO(
                        postId = 1,
                        postType = "질문",
                        postTitle = "우리집 포도에 이상한  증상이 생겼어요오오오이이잉",
                        postMemberName = "거꾸로 해도 토마토",
                        postDate = "2023-10-09",
                        likeCount = 10,
                        commentCount = 18,
                        postImage = "http://www.newsam.co.kr/data/photos/20180729/art_15318011131465_9b3bf5.jpg"
                    ),
                    PostVO(
                        postId = 1,
                        postType = "질문",
                        postTitle = "우리집 포도에 이상한  증상이 생겼어요오오오이이잉",
                        postMemberName = "거꾸로 해도 토마토",
                        postDate = "2023-10-11",
                        likeCount = 10,
                        commentCount = 18,
                        postImage = null
                    ),
                    PostVO(
                        postId = 1,
                        postType = "질문",
                        postTitle = "우리집 포도에 이상한  증상이 생겼어요오오오이이잉",
                        postMemberName = "거꾸로 해도 토마토",
                        postDate = "2023-10-13",
                        likeCount = 10,
                        commentCount = 18,
                        postImage = null
                    )
                )
            )
        }
    }

}