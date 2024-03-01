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
    private val _myPlantList = MutableLiveData<ApiState<List<MyPlantVO>>>()
    val myPlantList: LiveData<ApiState<List<MyPlantVO>>> = _myPlantList

    fun getMyPlantList() = viewModelScope.launch(Dispatchers.IO) {
        _myPlantList.postValue(repository.getMyPlantList())
    }

}