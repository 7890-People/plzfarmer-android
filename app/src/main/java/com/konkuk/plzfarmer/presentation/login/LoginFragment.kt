package com.konkuk.plzfarmer.presentation.login

import BaseFragment
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val TAG: String
        get() = "LoginFragment"
    override val layoutRes: Int
        get() = R.layout.fragment_login

//    val viewModel: LoginViewModel by activityViewModels()

    override fun afterViewCreated() {
//        binding.kakaoBtn.setOnClickListener {
//            onKakaoBtnClicked()
//
//        }
    }

    private fun onKakaoLoginSucceed() {
        //if(isRegistered)
//        viewModel.goto(LoginPage.JOIN1)
        //else
        //회원가입
    }


//    private val kakaoCallBack: (OAuthToken?, Throwable?) -> Unit = { token, error ->
//        if (error != null) {
//            Log.e(TAG, "카카오계정으로 로그인 실패", error)
//        } else if (token != null) {
//            Log.i(TAG, "카카오계정으로 로그인 성공")
//            val pair = getUserInfo()
//            viewModel.requestLogin(id = pair.first, email = pair.second)
//
//            //나중에 서버요청 observe필드로 이동 예정
//            onKakaoLoginSucceed()
//        }
//    }


//    private fun onKakaoBtnClicked() {
//        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
//            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
//                if (error != null) {
//                    Log.e(TAG, "카카오톡으로 로그인 실패", error)
//                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
//                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
//                        return@loginWithKakaoTalk
//                    }
//                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
//                    UserApiClient.instance.loginWithKakaoAccount(
//                        requireContext(), callback = kakaoCallBack
//                    )
//                } else if (token != null) {
//                    Log.i(TAG, "카카오톡으로 로그인 성공")
//                    val pair = getUserInfo()
//                    viewModel.requestLogin(id = pair.first, email = pair.second)
//                }
//            }
//        } else {
//            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = kakaoCallBack)
//        }
//    }

//    private fun getUserInfo(): Pair<String, String> {
//        //카카오 서버로부터 계정의 id와 email을 가져옴
//        var id = ""
//        var email = ""
//        UserApiClient.instance.me { user, error ->
//            if (error != null) {
//                Log.e(TAG, "사용자 정보 요청 실패", error)
//            } else if (user != null) {
//                id = user.id.toString()
//                email = user.kakaoAccount?.email.toString()
//                Log.d(TAG, "111$id, $email")
//            }
//        }
//        Log.d(TAG, "222$id, $email")
//        return Pair(id, email)
//    }
}