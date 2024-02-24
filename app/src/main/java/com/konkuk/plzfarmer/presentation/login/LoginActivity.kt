package com.konkuk.plzfarmer.presentation.login

import SignUpFarmInfoFragment
import SignUpHasFarmFragment
import androidx.fragment.app.commit
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.ActivityLoginBinding
import com.konkuk.plzfarmer.presentation.base.BaseActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override val layoutRes: Int
        get() = R.layout.activity_login

    override fun initViewModel() {

    }

    override fun afterViewCreated() {
        supportFragmentManager.commit {
            add(binding.loginFrameLayout.id, SignUpFarmInfoFragment(), "Signup")
        }
    }
}