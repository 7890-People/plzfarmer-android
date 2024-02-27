package com.konkuk.plzfarmer.presentation.login

import SignUpFarmInfoFragment
import SignUpHasFarmFragment
import SignUpNicknameFragment
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.ActivityLoginBinding
import com.konkuk.plzfarmer.presentation.base.BaseActivity
import com.konkuk.plzfarmer.remote.repository.AuthRepository
import com.konkuk.plzfarmer.utils.ViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override val layoutRes: Int
        get() = R.layout.activity_login

    private val authViewModel by lazy {
        ViewModelProvider(
            this, ViewModelFactory(AuthRepository())
        )[AuthViewModel::class.java]
    }

    override fun afterViewCreated() {
        collectPage()
        setBackBtn()
    }

    private val loginFragment by lazy {
        supportFragmentManager.findFragmentByTag(LoginFragment::class.java.name) ?: LoginFragment()
    }

    private val nicknameFragment by lazy {
        supportFragmentManager.findFragmentByTag(SignUpNicknameFragment::class.java.name)
            ?: SignUpNicknameFragment()
    }

    private val hasFarmFragment by lazy {
        supportFragmentManager.findFragmentByTag(SignUpHasFarmFragment::class.java.name)
            ?: SignUpHasFarmFragment()
    }

    private val farmInfoFragment by lazy {
        supportFragmentManager.findFragmentByTag(SignUpFarmInfoFragment::class.java.name)
            ?: SignUpFarmInfoFragment()
    }


    private fun getFragment(page: LoginPage): Fragment {
        return when (page) {
            LoginPage.LOGIN -> loginFragment
            LoginPage.NICKNAME -> nicknameFragment
            LoginPage.HASFARM -> hasFarmFragment
            LoginPage.FARMINFO -> farmInfoFragment
        }
    }

    private fun collectPage() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                var prevPage = authViewModel.pageFlow.value
                authViewModel.pageFlow.collect { page ->
                    val preFragment = getFragment(prevPage)
                    val fragment = getFragment(page)
                    supportFragmentManager.commit {
                        if (preFragment != fragment) hide(preFragment)
                        if (fragment.isAdded) show(fragment)
                        else add(R.id.loginFrameLayout, fragment, fragment.javaClass.name)
                    }
                    prevPage = page
                }
            }
        }
    }

    private var backButtonPressedOnce = false
    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (backButtonPressedOnce) finish()
            else {
                backButtonPressedOnce = true
                showToast("한 번 더 누르면 종료됩니다")
                lifecycleScope.launch {
                    delay(2000)
                    backButtonPressedOnce = false
                }
            }
        }
    }

    private fun setBackBtn() {
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }
}