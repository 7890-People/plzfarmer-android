package com.konkuk.plzfarmer.presentation.main

import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.ActivityMainBinding
import com.konkuk.plzfarmer.presentation.base.BaseActivity
import com.konkuk.plzfarmer.presentation.main.home.HomeFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val TAG: String = "MainActivity"
    override val layoutRes: Int = R.layout.activity_main
    lateinit var mainViewModel: MainViewModel

    private val homeFragment by lazy {
        supportFragmentManager.findFragmentByTag(HomeFragment::class.java.name)
            ?: HomeFragment()
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun afterViewCreated() {
        collectPage()
        collectBtnvFlow()
        setBottomNavi()
        setBackBtn()
    }

    private fun getFragment(page: MainPage): Fragment {
        return when (page) {
            MainPage.HOME -> homeFragment
            MainPage.SEARCH -> homeFragment
            MainPage.COMMUNITY -> homeFragment
            MainPage.NOTICE -> homeFragment
        }
    }

    private fun collectBtnvFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.btnvFlow.collect {
                    setBtnvVisibility(it)
                }
            }
        }
    }


    private fun collectPage() {
        Log.d(TAG, "collectPage 시작")
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                var prevPage = mainViewModel.pageFlow.value
                mainViewModel.pageFlow.collect { page ->
                    Log.d(TAG, " $page collect됨")
                    val preFragment = getFragment(prevPage)
                    val fragment = getFragment(page)
                    supportFragmentManager.commit {
                        if (preFragment != fragment) hide(preFragment)
                        if (fragment.isAdded) {
                            show(fragment)
                        } else add(R.id.mainFragmentContainer, fragment, fragment.javaClass.name)
                    }
                    prevPage = page
                }
            }
        }
    }

    // 뒤로가기
    private fun setBackBtn() {
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private var backButtonPressedOnce = false
    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {

            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
                return
            }

            when (supportFragmentManager.fragments.first { it.isVisible }) {

                else -> {
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
        }
    }

    // 바텀 네비게이션
    private fun setBottomNavi() {
        binding.btnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btnv_home -> {
                    mainViewModel.gotoPage(MainPage.HOME)
                }
                R.id.btnv_search -> {
                    mainViewModel.gotoPage(MainPage.SEARCH)
                }
                R.id.btnv_community -> {
                    mainViewModel.gotoPage(MainPage.COMMUNITY)
                }
                R.id.btnv_notice -> {
                    mainViewModel.gotoPage(MainPage.NOTICE)
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    fun setBtnvVisibility(visibility: Boolean) {
        binding.bottomAppBar.visibility = if (visibility) View.VISIBLE else View.GONE
        binding.fab.visibility = if (visibility) View.VISIBLE else View.GONE
    }
}