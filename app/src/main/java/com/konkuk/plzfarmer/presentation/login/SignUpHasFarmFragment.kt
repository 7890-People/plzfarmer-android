import androidx.fragment.app.activityViewModels
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.FragmentSignupHasFarmBinding
import com.konkuk.plzfarmer.presentation.base.BaseFragment
import com.konkuk.plzfarmer.presentation.login.AuthViewModel
import com.konkuk.plzfarmer.presentation.login.LoginPage

class SignUpHasFarmFragment : BaseFragment<FragmentSignupHasFarmBinding>() {
    override val layoutRes: Int = R.layout.fragment_signup_has_farm

    val viewModel: AuthViewModel by activityViewModels()

    override fun afterViewCreated() {
        binding.apply {
            nextBtn.setOnClickListener {
                if (yesBtn.isSelected) viewModel.gotoFragment(LoginPage.FARMINFO)
                else if (noBtn.isSelected) {
                    //TODO: 가입 POST
                    viewModel.requestSignUp()
                    //TODO: gotoMainActivity
                } else{
                    showToast("아무것도 선택하지 않았어요")
                }
            }
        }
    }

}