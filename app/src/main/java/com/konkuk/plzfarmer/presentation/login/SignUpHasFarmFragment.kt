import androidx.fragment.app.activityViewModels
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.FragmentSignupHasFarmBinding
import com.konkuk.plzfarmer.presentation.login.AuthViewModel
import com.konkuk.plzfarmer.presentation.login.LoginPage

class SignUpHasFarmFragment : BaseFragment<FragmentSignupHasFarmBinding>() {
    override val layoutRes: Int = R.layout.fragment_signup_has_farm

    val viewModel: AuthViewModel by activityViewModels()

    override fun afterViewCreated() {
        binding.apply {
            nextBtn.setOnClickListener {
                if (yesBtn.isSelected) viewModel.gotoFragment(LoginPage.FARMINFO)
                else {
                    //gotoMainActivity
                }
            }
        }
    }

}