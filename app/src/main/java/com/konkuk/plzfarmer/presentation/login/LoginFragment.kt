package com.konkuk.plzfarmer.presentation.login


import android.util.Log
import androidx.fragment.app.activityViewModels
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.FragmentLoginBinding
import com.konkuk.plzfarmer.presentation.base.BaseFragment
import com.konkuk.plzfarmer.remote.request.SignUpRequest

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val TAG: String
        get() = "LoginFragment"
    override val layoutRes: Int
        get() = R.layout.fragment_login

    val viewModel: AuthViewModel by activityViewModels()

    override fun afterViewCreated() {
        binding.btnKakao.setOnClickListener {
            try {
                onKakaoBtnClicked()
            } catch (e: Exception) {
                Log.e(TAG, e.stackTraceToString())
            }
        }

        viewModel.loginResponse.observe(this) {
            it.byState(
                onSuccess = {

                },
                onError = {
                    if (it.status == 404) {
                        viewModel.gotoFragment(LoginPage.NICKNAME)
                    }
                },
                onLoading = {

                })
        }
    }

    private fun onKakaoLoginSucceed(id: String, email: String) {
        viewModel.requestLogin(id)
        viewModel.signUpRequest = SignUpRequest(userEmail = email, userId = id)
    }

    private fun onKakaoBtnClicked() {
        if (!UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoAccount(
                requireContext(), callback = kakaoCallBack
            )
            return
        }

        UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오톡으로 로그인 실패", error)
                // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }
                // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                UserApiClient.instance.loginWithKakaoAccount(
                    requireContext(), callback = kakaoCallBack
                )
            } else if (token != null) {
                Log.i(TAG, "카카오톡으로 로그인 성공")
                getUserInfo()
            }
        }
    }


    private val kakaoCallBack: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        //카카오톡 없는 경우 이메일로 로그인 후 콜백
        if (error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(TAG, "카카오계정으로 로그인 성공")
            getUserInfo()
        }
    }

    private fun getUserInfo() {
        //카카오 서버로부터 계정의 id와 email을 가져옴
        var id = ""
        var email = ""
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            } else if (user != null) {
                id = user.id.toString()
                email = user.kakaoAccount?.email.toString()
                onKakaoLoginSucceed(id, email)
            }
        }
    }
    
}