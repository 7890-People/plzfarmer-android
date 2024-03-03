package com.konkuk.plzfarmer.presentation.main.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.plzfarmer.presentation.main.community.main.PostVO
import com.konkuk.plzfarmer.remote.repository.CommunityRepository
import com.konkuk.plzfarmer.utils.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommunityViewModel(val repository: CommunityRepository): ViewModel() {
    // 게시글 조회
    private val _postList = MutableLiveData<ApiState<List<PostVO>>>()
    val postList: LiveData<ApiState<List<PostVO>>> = _postList

    fun getPostList(filter: String) = viewModelScope.launch(Dispatchers.IO) {
        _postList.postValue(repository.getPostList(filter))
    }
}