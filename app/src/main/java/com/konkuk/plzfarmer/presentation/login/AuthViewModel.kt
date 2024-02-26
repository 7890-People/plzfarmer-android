package com.konkuk.plzfarmer.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.plzfarmer.remote.repository.AuthRepository
import com.konkuk.plzfarmer.remote.request.SignUpRequest
import com.konkuk.plzfarmer.remote.response.DuplicateResponse
import com.konkuk.plzfarmer.remote.response.LoginResponse
import com.konkuk.plzfarmer.utils.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.sign

class AuthViewModel(val repository: AuthRepository) : ViewModel() {

    private val _pageFlow = MutableStateFlow(LoginPage.LOGIN)
    val pageFlow = _pageFlow.stateIn(viewModelScope, SharingStarted.Eagerly, LoginPage.LOGIN)

    fun gotoFragment(page: LoginPage) {
        viewModelScope.launch {
            _pageFlow.emit(page)
        }
    }

    private val _loginResponse = MutableLiveData<ApiState<out LoginResponse>>()
    val loginResponse: LiveData<ApiState<out LoginResponse>> = _loginResponse
    fun requestLogin(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loginResponse.postValue(ApiState.Loading)
            _loginResponse.postValue(repository.requestLogin(id))
        }
    }

    private val _duplicateResponse = MutableLiveData<ApiState<out DuplicateResponse>>()
    val duplicateResponse: LiveData<ApiState<out DuplicateResponse>> = _duplicateResponse

    fun checkNicknameDuplicate(nickname: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _duplicateResponse.postValue(repository.checkDuplicate(nickname))
            }
        }
    }


    fun requestSignUp() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.requestSignUp(signUpRequest)
            }
        }
    }

    lateinit var signUpRequest: SignUpRequest

}