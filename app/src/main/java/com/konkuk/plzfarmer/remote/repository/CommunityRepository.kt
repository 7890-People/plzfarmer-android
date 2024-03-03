package com.konkuk.plzfarmer.remote.repository

import com.konkuk.plzfarmer.presentation.base.BaseRepository
import com.konkuk.plzfarmer.presentation.main.community.detail.CommentVO
import com.konkuk.plzfarmer.presentation.main.community.main.PostVO
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

    suspend fun getCommentList(): ApiState<List<CommentVO>> {
        return ApiState.Success(
            data = listOf(
                CommentVO(
                    commentProfileImage = null,
                    commentWriterName = "졸프픙",
                    commentContent = "저는 이렇게 해결했어요!",
                    commentDateTime = "10-09  23:57"
                ),
                CommentVO(
                    commentProfileImage = null,
                    commentWriterName = "농부네 첫째",
                    commentContent = "글이 너무 멋져요!",
                    commentDateTime = "10-10  23:57"
                ),
                CommentVO(
                    commentProfileImage = null,
                    commentWriterName = "농부네 둘째",
                    commentContent = "글이 너무 도움돼요!",
                    commentDateTime = "10-11  13:23"
                ),
                CommentVO(
                    commentProfileImage = null,
                    commentWriterName = "농부네 셋째",
                    commentContent = "글이 너무 멋져요잉!! 토마토에 이런 이상이 있다니 마음이 너무 슬프네요... 힘내세요! 응원하겠습니다",
                    commentDateTime = "10-10  23:57"
                ),
                CommentVO(
                    commentProfileImage = null,
                    commentWriterName = "농부네 넷째",
                    commentContent = "글이 너무 도움돼요!",
                    commentDateTime = "10-11  13:23"
                )
            )
        )
    }

}