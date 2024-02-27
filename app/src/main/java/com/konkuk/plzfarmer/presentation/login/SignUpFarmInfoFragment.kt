
import android.util.Log
import androidx.fragment.app.activityViewModels
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.FramentSignupFarmInfoBinding
import com.konkuk.plzfarmer.presentation.login.AuthViewModel

class SignUpFarmInfoFragment : BaseFragment<FramentSignupFarmInfoBinding>() {
    override val layoutRes: Int
        get() = R.layout.frament_signup_farm_info

    val viewModel: AuthViewModel by activityViewModels()

    override fun afterViewCreated() {
        binding.apply {

            nextBtn.setOnClickListener {
                val farmName = edittextFarmName.text.toString()
                if (farmName.isBlank()) {
                    showToast("농장의 별명을 정해주세요")
                    return@setOnClickListener
                }
                val farmAddr = edittextFarmAddr.text.toString()
                if (farmAddr.isBlank()) {
                    showToast("농장의 우편번호를 입력해주세요")
                    return@setOnClickListener
                }
                if (!btnInside.isSelected && !btnOutside.isSelected) {
                    showToast("농장이 실내/실외에 있는 지 선택해주세요")
                    return@setOnClickListener
                }

                //회원가입완료
                viewModel.signUpRequest = viewModel.signUpRequest.copy(
                    farmName = farmName, farmAddress = farmAddr, isFarmIndoor = btnInside.isSelected
                )
                viewModel.requestSignUp()
                Log.d(TAG, viewModel.signUpRequest.toString())
            }
        }
    }
    
    
}