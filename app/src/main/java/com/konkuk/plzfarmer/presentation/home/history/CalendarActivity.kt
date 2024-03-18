package com.konkuk.plzfarmer.presentation.home.history

import androidx.lifecycle.lifecycleScope
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.ActivityCalendarBinding
import com.konkuk.plzfarmer.presentation.base.BaseActivity
import com.konkuk.plzfarmer.remote.repository.PreferenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.Period

class CalendarActivity : BaseActivity<ActivityCalendarBinding>() {
    override val layoutRes: Int
        get() = R.layout.activity_calendar


    override fun afterViewCreated() {
        lifecycleScope.launch {
            tmpFirstInit()
            val firstDateString = withContext(Dispatchers.IO) {
                val prefRepository = PreferenceRepository(this@CalendarActivity)
                prefRepository.getFirstDate()
            }
            val firstDate = LocalDate.parse(firstDateString)

            val monthsDiff = Period.between(firstDate, LocalDate.now()).months

            binding.viewPager.adapter =
                CalendarFragmentStateAdapter(this@CalendarActivity, firstDate, monthsDiff+1)
            binding.viewPager.setCurrentItem(monthsDiff, false)
        }

    }

    private suspend fun tmpFirstInit() {
        val prefRepository = PreferenceRepository(this@CalendarActivity)
        prefRepository.saveIsFirstInstall()
        prefRepository.saveFirstDate("2023-11-01")
    }

    fun plusMonth(months: Int) {
        val current = binding.viewPager.currentItem
        binding.viewPager.setCurrentItem(current + months, true)
    }


}