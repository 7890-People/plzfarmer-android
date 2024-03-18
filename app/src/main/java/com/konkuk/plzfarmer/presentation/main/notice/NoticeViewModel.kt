package com.konkuk.plzfarmer.presentation.main.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.plzfarmer.remote.repository.NoticeRepository
import com.konkuk.plzfarmer.utils.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoticeViewModel(val repository: NoticeRepository): ViewModel() {
    // 알림 조회
    private val _noticeList = MutableLiveData<ApiState<List<NoticeVO>>>()
    val noticeList: LiveData<ApiState<List<NoticeVO>>> = _noticeList

    fun getNoticeList() = viewModelScope.launch(Dispatchers.IO) {
        _noticeList.postValue(repository.getNoticeList())
    }
}