package com.konkuk.plzfarmer.presentation.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.plzfarmer.remote.repository.HomeRepository
import com.konkuk.plzfarmer.utils.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(val repository: HomeRepository): ViewModel() {
    // 나의 식물
    private val _myPlantList = MutableLiveData<ApiState<List<MyPlantVO>>>()
    val myPlantList: LiveData<ApiState<List<MyPlantVO>>> = _myPlantList

    fun getMyPlantList() = viewModelScope.launch(Dispatchers.IO) {
        _myPlantList.postValue(repository.getMyPlantList())
    }

    // 최근 진단 기록
    private val _recentHistoryList = MutableLiveData<ApiState<List<RecentHistoryItemVO>>>()
    val recentHistoryList: LiveData<ApiState<List<RecentHistoryItemVO>>> = _recentHistoryList

    fun getRecentHistoryList() = viewModelScope.launch(Dispatchers.IO) {
        _recentHistoryList.postValue(repository.getRecentHistoryList())
    }

}