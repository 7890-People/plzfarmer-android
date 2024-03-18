package com.konkuk.plzfarmer.presentation.main.home.history.calendar

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.FragmentHistoryCalendarBinding
import com.konkuk.plzfarmer.presentation.base.BaseFragment
import com.konkuk.plzfarmer.presentation.main.home.history.CalendarActivity
import com.konkuk.plzfarmer.remote.response.DateHistory
import com.konkuk.plzfarmer.remote.response.DiseaseHistory
import com.konkuk.plzfarmer.utils.customGetSerializable
import java.time.LocalDate

class HistoryCalendarFragment : BaseFragment<FragmentHistoryCalendarBinding>() {

    companion object {
        fun newInstance(currentDate: LocalDate, position: Int): HistoryCalendarFragment {
            val args = Bundle()
            args.putSerializable("currentDate", currentDate)
            args.putInt("position", position)
            val fragment = HistoryCalendarFragment()
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var adapter: CalendarHistoryListAdapter
    override val layoutRes: Int
        get() = R.layout.fragment_history_calendar

    private lateinit var currentDate: LocalDate
    override fun afterViewCreated() {
        currentDate = arguments?.customGetSerializable<LocalDate>("currentDate") ?: LocalDate.now()
        val position = arguments?.getInt("position") ?: 0
        showToast("Position [$position] Fragment viewCreated")
        Log.d(TAG, "position: $position")
        initCalendar()
        initRecycler()
        initMonthBtn()
    }

    private fun initMonthBtn() {
        val activity = requireActivity() as CalendarActivity
        binding.calendarNextBtn.setOnClickListener {
            activity.plusMonth(1)
        }
        binding.calendarPrevBtn.setOnClickListener {
            activity.plusMonth(-1)
        }
    }

    private fun initRecycler() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = CalendarHistoryListAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun initCalendar() {
        val yearMonthStr = "${currentDate.year}년 ${currentDate.monthValue}월"
        binding.yearMonthTextView.text = yearMonthStr

        val onDateClickListener = object : CalendarDateView.OnDateClickListener {
            override fun onClick(history: DateHistory?, date: LocalDate) {
                binding.emptyDateText.text = date.toString()
                binding.recyclerDateText.text = date.toString()

                if (history == null) {
                    adapter.historyList = emptyList()
                    binding.emptyIndicatorLayout.visibility = View.VISIBLE
                    binding.recyclerDateText.visibility = View.GONE
                } else {
                    adapter.historyList = history.historyList
                    binding.emptyIndicatorLayout.visibility = View.GONE
                    binding.recyclerDateText.visibility = View.VISIBLE
                }
                adapter.notifyDataSetChanged()
            }
        }

        val histories = mutableListOf<DateHistory>()
        histories.add(
            DateHistory(
                date = "2024-03-11", historyList = listOf(
                    DiseaseHistory(
                        id = "1",
                        diseaseKrName = "탄저병",
                        diseaseEngName = "TanJuByung",
                        plantName = "포도",
                        imageUrl = "https://www.farmnmarket.com/data/photos/20180937/art_1537088091751_3a1628.jpg"
                    )
                )
            )
        )
        histories.add(
            DateHistory(
                date = "2024-03-01", historyList = listOf(
                    DiseaseHistory(
                        id = "2",
                        diseaseKrName = "탄저병",
                        diseaseEngName = "TanJuByung",
                        plantName = "포도",
                        imageUrl = "https://www.farmnmarket.com/data/photos/20180937/art_1537088091751_3a1628.jpg"
                    )
                )
            )
        )
        binding.calendarView.drawCalendar(
            currentDate.atStartOfDay(),
            histories,
            onDateClickListener
        )

    }
}