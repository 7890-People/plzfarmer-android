package com.konkuk.plzfarmer.presentation.main.home.history.calendar

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.ItemCalendarDateBinding
import com.konkuk.plzfarmer.remote.response.DateHistory
import java.time.LocalDate
import java.time.LocalDateTime

class CalendarDateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    lateinit var day: LocalDateTime
    var history: DateHistory? = null
    lateinit var dateClickListener: OnDateClickListener

    val TAG = "CalendarDate"

    val binding = ItemCalendarDateBinding.bind(
        LayoutInflater.from(context).inflate(R.layout.item_calendar_date, this, false)
    )

    init {
        addView(binding.root)
    }

    fun initDate(
        day: LocalDateTime,
        history: DateHistory?,
        dateClickListener: OnDateClickListener
    ) {
        this.day = day
        this.history = history
        this.dateClickListener = dateClickListener

        Log.d(TAG, "initDate: ${day.dayOfMonth}")
        binding.calendarDateTextView.text = day.dayOfMonth.toString()
        history?.let {
            Glide.with(context).load(it.historyList[0].imageUrl).into(binding.calendarDateImageView)
            binding.calendarDateImageView.clipToOutline = true
        }

        binding.root.setOnClickListener {
            dateClickListener.onClick(history, day.toLocalDate())
        }
        invalidate()
    }

    interface OnDateClickListener {
        fun onClick(history: DateHistory?, date: LocalDate)
    }
}
