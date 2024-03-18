package com.konkuk.plzfarmer.presentation.main.community.main

class PostVO (
    //커뮤니티 게시글 recyclerView 아이템 데이터
    val postId: Int,
    val postType: String, // 질문, 잡담
    val postTitle: String,
    val postMemberName: String,
    val postDate: String,
    val likeCount: Int,
    val commentCount: Int,
    val postImage: String?
)