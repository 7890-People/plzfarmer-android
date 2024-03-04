package com.konkuk.plzfarmer.presentation.history

import androidx.recyclerview.widget.GridLayoutManager
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.FragmentCalendarBinding
import com.konkuk.plzfarmer.presentation.base.BaseActivity
import java.util.Calendar

class CalendarActivity : BaseActivity<FragmentCalendarBinding>() {
    override val layoutRes: Int
        get() = R.layout.fragment_calendar

    override fun afterViewCreated() {
        binding.calendarView.apply {
            layoutManager = GridLayoutManager(this@CalendarActivity, 7)
            setItemViewCacheSize(42)
            setHasFixedSize(true)
        }

//        setCalendarView(getCalendarInstance())
    }
//
//    private fun setCalendarView(calendar: Calendar) {
//
//        dayList.clear();
//
//        // 이번달 시작 요일
//        val dayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
//        val thisMonthLastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//
//        calendar.add(Calendar.MONTH, -1);
//
//        // 지난달 마지막 일자
//        val lastMonthStartDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        calendar.add(Calendar.MONTH, 1);
//
//        lastMonthStartDay -= (dayOfMonth - 1) - 1;
//
//        // 년월 표시
//        tv_month.setText((this.calendar.get(Calendar.MONTH) + 1) + "");
//
//        Day day;
//        for (int i = 0; i < dayOfMonth - 1; i++) {
//            int date = lastMonthStartDay +i;
//            day = new Day ();
//            day.setDay(Integer.toString(date));
//            day.setInMonth(false);
//
//            dayList.add(day);
//
//        }
//    }

    private fun getCalendarInstance(): Calendar {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        return calendar
    }
}