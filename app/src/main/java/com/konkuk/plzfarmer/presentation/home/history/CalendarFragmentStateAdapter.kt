package com.konkuk.plzfarmer.presentation.home.history

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.time.LocalDate

class CalendarFragmentStateAdapter(fragmentActivity: FragmentActivity, val firstDate: LocalDate, val itemSize: Int) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = itemSize

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun containsItem(itemId: Long): Boolean = 200000L < itemId && itemId < 210001L

    override fun createFragment(position: Int): Fragment {
        return HistoryCalendarFragment.newInstance(
            firstDate.plusMonths(position.toLong()),
            position
        )
    }


}