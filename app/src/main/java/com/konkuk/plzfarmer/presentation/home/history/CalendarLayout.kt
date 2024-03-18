package com.konkuk.plzfarmer.presentation.home.history

import CalendarDateView
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.view.children
import com.konkuk.plzfarmer.remote.response.DateHistory
import java.time.LocalDateTime
import java.time.YearMonth
import kotlin.math.roundToInt

class CalendarLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr) {

    private val DAYS_PER_WEEK = 7
    private val WEEKS_PER_MONTH = 7

    init {
        rowCount = 6
        columnCount = DAYS_PER_WEEK
    }

    fun drawCalendar(
        currentDate: LocalDateTime = LocalDateTime.now(),
        histories: List<DateHistory>,
        dateClickListener: CalendarDateView.OnDateClickListener
    ) {
        val yearMonth = YearMonth.from(currentDate)
        val daysInMonth = yearMonth.lengthOfMonth()
        var date = currentDate.withDayOfMonth(1).toLocalDate().atStartOfDay()
        val dayOfWeekOfFirst = date.dayOfWeek.value % DAYS_PER_WEEK

        for (i in 1..dayOfWeekOfFirst) {
            val emptyView = CalendarDateView(context)
            emptyView.visibility = View.INVISIBLE
            addView(emptyView)
        }

        for (i in 1..daysInMonth) {
            val dateView = CalendarDateView(context)
            dateView.initDate(date, histories.find { date == it.localDate }, dateClickListener)
            date = date.plusDays(1)
            dateView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT).apply { foregroundGravity = Gravity.FILL }
            addView(dateView)
        }

        requestLayout()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val iWidth = (width / DAYS_PER_WEEK).toFloat()
        val iHeight = (height / WEEKS_PER_MONTH).toFloat()

        var index = 0
        children.forEach {
            val left = (index % DAYS_PER_WEEK) * iWidth
            val top = (index / DAYS_PER_WEEK) * iHeight
            it.layout(left.toInt(), top.toInt(), (left + iWidth).toInt(), (top + iHeight).toInt())
            index++
        }

        super.onLayout(changed, left, top, right, bottom)
    }

    private fun dpToPx(dp: Float): Int {
        return (dp * resources.displayMetrics.density).roundToInt()
    }

}
