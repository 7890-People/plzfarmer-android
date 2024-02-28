import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.FragmentSignupNicknameBinding
import com.konkuk.plzfarmer.presentation.base.BaseFragment
import com.konkuk.plzfarmer.presentation.login.AuthViewModel
import com.konkuk.plzfarmer.presentation.login.LoginPage

class SignUpNicknameFragment : BaseFragment<FragmentSignupNicknameBinding>() {
    override val layoutRes: Int = R.layout.fragment_signup_nickname
    val viewModel: AuthViewModel by activityViewModels()

    override fun afterViewCreated() {
        binding.nextBtn.setOnClickListener {
            viewModel.gotoFragment(LoginPage.HASFARM)
        }

        binding.apply {
            editTextNickname.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val text = editTextNickname.text.toString()
                    if (text.length < 2 || text.length > 6) {
                        checkBtn.isEnabled = false
                        nextBtn.isEnabled = false
                    } else {
                        checkBtn.isEnabled = true
                    }
                    if(nextBtn.isEnabled) nextBtn.isEnabled = false
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })

            checkBtn.setOnClickListener {
                viewModel.checkNicknameDuplicate(binding.editTextNickname.text.toString())
            }

            viewModel.duplicateResponse.observe(this@SignUpNicknameFragment) {
                it.byState(
                    onSuccess = { response ->
                        if (response.canUse) {
                            showToast("사용 가능한 닉네임입니다")
                            nextBtn.isEnabled = true
                        } else {
                            showToast("이미 사용 중인 닉네임입니다.")
                            nextBtn.isEnabled = false
                        }
                    }
                )
            }

            nextBtn.setOnClickListener {
                viewModel.signUpRequest = viewModel.signUpRequest.copy(userNickname = editTextNickname.text.toString())
                viewModel.gotoFragment(LoginPage.HASFARM)
            }
        }
    }


}